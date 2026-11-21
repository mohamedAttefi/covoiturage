package com.covoiturage.service.impl;

import com.covoiturage.dto.ReservationRequest;
import com.covoiturage.dto.ReservationResponse;
import com.covoiturage.entity.*;
import com.covoiturage.enums.*;
import com.covoiturage.exception.*;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.*;
import com.covoiturage.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;
    private final TrajetRepository trajetRepository;
    private final ReservationMapper mapper;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            PassagerRepository passagerRepository,
            TrajetRepository trajetRepository,
            ReservationMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.passagerRepository = passagerRepository;
        this.trajetRepository = trajetRepository;
        this.mapper = mapper;
    }

    @Override
    public ReservationResponse create(ReservationRequest request) {

        Passager passager = passagerRepository.findById(request.passagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        Trajet trajet = trajetRepository.findById(request.trajetId())
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found"));

        if (trajet.getStatut() != StatutTrajet.PUBLIE) {
            throw new BusinessRuleException("Journey is not available");
        }

        if (reservationRepository.existsByPassager_IdAndTrajet_IdAndStatut(
                passager.getId(), trajet.getId(), StatutReservation.CONFIRMEE)) {
            throw new BusinessRuleException("Passenger already has a reservation");
        }

        PointArret montee = trajet.getPointsArret().stream()
                .filter(p -> p.getId().equals(request.pointMonteeId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Boarding stop not found"));

        PointArret descente = trajet.getPointsArret().stream()
                .filter(p -> p.getId().equals(request.pointDescenteId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Arrival stop not found"));

        if (montee.getOrdre() >= descente.getOrdre()) {
            throw new BusinessRuleException("Invalid boarding and arrival stops");
        }

        long reservedSeats = reservationRepository.sumReservedSeats(
                trajet.getId(), StatutReservation.CONFIRMEE);

        if (reservedSeats + request.nombrePlaces() > trajet.getPlacesProposees()) {
            throw new BusinessRuleException("Not enough available seats");
        }

        Reservation reservation = mapper.toEntity(request);
        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPointMontee(montee);
        reservation.setPointDescente(descente);
        reservation.setMontantTotal(
                descente.getPrixDepuisDepart()
                        .subtract(montee.getPrixDepuisDepart())
                        .multiply(java.math.BigDecimal.valueOf(request.nombrePlaces()))
        );
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation saved = reservationRepository.save(reservation);

        if (reservedSeats + request.nombrePlaces() == trajet.getPlacesProposees()) {
            trajet.setStatut(StatutTrajet.COMPLET);
        }

        return mapper.toResponse(saved);
    }






}