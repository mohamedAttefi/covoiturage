package com.covoiturage.service.impl;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import com.covoiturage.dto.ReservationResponse;
import com.covoiturage.entity.*;
import com.covoiturage.enums.StatutReservation;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.exception.InvalidRequestException;
import com.covoiturage.exception.ForbiddenActionException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.*;
import com.covoiturage.service.TrajetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final ConducteurRepository conducteurRepository;
    private final ReservationRepository reservationRepository;
    private final VehiculeRepository vehiculeRepository;
    private final TrajetMapper mapper;

    public TrajetServiceImpl(
            TrajetRepository trajetRepository,
            ConducteurRepository conducteurRepository, ReservationRepository reservationRepository,
            VehiculeRepository vehiculeRepository,
            TrajetMapper mapper) {
        this.trajetRepository = trajetRepository;
        this.conducteurRepository = conducteurRepository;
        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.mapper = mapper;
    }

    @Override
    public TrajetResponse create(TrajetRequest request) {

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        Vehicule vehicule = vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        if (!vehicule.getConducteur().getId().equals(conducteur.getId())) {
            throw new ForbiddenActionException("This vehicle does not belong to this driver");
        }

        if (request.placesProposees() < 1 || request.placesProposees() > vehicule.getCapacite()) {
            throw new InvalidRequestException("Proposed seats must be between 1 and vehicle capacity");
        }

        validateStops(request.pointsArret());

        Trajet trajet = mapper.toEntity(request);
        trajet.setConducteur(conducteur);
        trajet.setVehicule(vehicule);
        trajet.setStatut(StatutTrajet.PUBLIE);

        for (PointArret point : trajet.getPointsArret()) {
            point.setTrajet(trajet);
        }

        return mapper.toResponse(trajetRepository.save(trajet));
    }

    private void validateStops(List<PointArretRequest> stops) {

        if (stops == null || stops.size() < 2) {
            throw new InvalidRequestException("A journey needs at least two stops");
        }

        if (stops.get(0).ordre() != 0) {
            throw new InvalidRequestException(
                    "The first stop must have order 0"
            );
        }

        if (stops.get(0).heurePassage().isBefore(LocalDateTime.now())) {
            throw new InvalidRequestException("Departure must be in the future");
        }

        if (stops.get(0).prixDepuisDepart().compareTo(BigDecimal.ZERO) != 0) {
            throw new InvalidRequestException("The first stop price must be zero");
        }

        Set<String> cities = new HashSet<>();

        for (int i = 0; i < stops.size(); i++) {
            PointArretRequest current = stops.get(i);

            if (!cities.add(current.ville().trim().toLowerCase())) {
                throw new InvalidRequestException("A city cannot appear twice");
            }

            if (i > 0) {
                PointArretRequest previous = stops.get(i - 1);

                if (current.ordre() <= previous.ordre()) {
                    throw new InvalidRequestException("Stop order must increase");
                }

                if (!current.heurePassage().isAfter(previous.heurePassage())) {
                    throw new InvalidRequestException("Stop times must increase");
                }

                if (current.prixDepuisDepart().compareTo(previous.prixDepuisDepart()) <= 0) {
                    throw new InvalidRequestException("Stop prices must increase");
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrajetResponse> findAll() {
        return trajetRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrajetResponse findById(Long id) {
        Trajet trajet = trajetRepository.findDetailedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

        return mapper.toResponse(trajet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrajetResponse> search(
            String depart,
            String arrivee,
            LocalDate date,
            int nombrePlaces,
            BigDecimal prixMax,
            Pageable pageable
    ) {
        if (nombrePlaces < 1) {
            throw new InvalidRequestException("Number of seats must be positive");
        }

        if (pageable.getPageNumber() < 0 || pageable.getPageSize() < 1) {
            throw new InvalidRequestException("Invalid pagination parameters");
        }

        if (prixMax != null && prixMax.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRequestException("Maximum price cannot be negative");
        }

        return trajetRepository.searchTrajetsDisponibles(
                depart,
                arrivee,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay(),
                StatutTrajet.PUBLIE,
                StatutReservation.CONFIRMEE,
                nombrePlaces,
                prixMax,
                pageable
        ).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> reservations(Long id, Long conducteurId) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));
        if (!trajet.getConducteur().getId().equals(conducteurId)) {
            throw new ForbiddenActionException("Only the journey owner can view its reservations");
        }
        return reservationRepository.findByTrajet_IdAndStatut(id, StatutReservation.CONFIRMEE)
                .stream().map(reservation -> new ReservationResponse(
                        reservation.getId(), reservation.getPassager().getId(), id,
                        reservation.getPointMontee().getId(), reservation.getPointDescente().getId(),
                        reservation.getNombrePlaces(), reservation.getMontantTotal(),
                        reservation.getDateReservation(), reservation.getStatut()))
                .toList();
    }

    @Override
    public void cancel(Long id, Long conducteurId) {
        Trajet trajet = trajetRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

        if (!trajet.getConducteur().getId().equals(conducteurId)) {
            throw new ForbiddenActionException("Only the journey owner can cancel it");
        }

        if (trajet.getStatut() == StatutTrajet.TERMINE ||
                trajet.getStatut() == StatutTrajet.ANNULE) {
            throw new BusinessRuleException("Cannot cancel this journey");
        }

        trajet.setStatut(StatutTrajet.ANNULE);

        List<Reservation> reservations =
                reservationRepository.findByTrajet_IdAndStatut(
                        id,
                        StatutReservation.CONFIRMEE
                );

        for (Reservation reservation : reservations) {
            reservation.setStatut(StatutReservation.ANNULEE);
        }
    }

    @Override
    public void finish(Long id, Long conducteurId) {
        Trajet trajet = trajetRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

        if (!trajet.getConducteur().getId().equals(conducteurId)) {
            throw new ForbiddenActionException("Only the journey owner can finish it");
        }

        if (trajet.getStatut() == StatutTrajet.TERMINE ||
                trajet.getStatut() == StatutTrajet.ANNULE) {
            throw new BusinessRuleException("Cannot finish this journey");
        }

        if (trajet.getPointsArret().get(0).getHeurePassage()
                .isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException(
                    "A journey cannot be finished before departure"
            );
        }

        trajet.setStatut(StatutTrajet.TERMINE);
    }
}
