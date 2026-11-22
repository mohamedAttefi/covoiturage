package com.covoiturage.service.impl;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import com.covoiturage.entity.*;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.*;
import com.covoiturage.service.TrajetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class  TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final ConducteurRepository conducteurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final TrajetMapper mapper;

    public TrajetServiceImpl(
            TrajetRepository trajetRepository,
            ConducteurRepository conducteurRepository,
            VehiculeRepository vehiculeRepository,
            TrajetMapper mapper) {
        this.trajetRepository = trajetRepository;
        this.conducteurRepository = conducteurRepository;
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
            throw new BusinessRuleException("This vehicle does not belong to this driver");
        }

        if (request.placesProposees() > vehicule.getCapacite()) {
            throw new BusinessRuleException("Proposed seats exceed vehicle capacity");
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
            throw new BusinessRuleException("A journey needs at least two stops");
        }

        if (stops.get(0).heurePassage().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("Departure must be in the future");
        }

        if (stops.get(0).prixDepuisDepart().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException("The first stop price must be zero");
        }

        Set<String> cities = new HashSet<>();

        for (int i = 0; i < stops.size(); i++) {
            PointArretRequest current = stops.get(i);

            if (!cities.add(current.ville().trim().toLowerCase())) {
                throw new BusinessRuleException("A city cannot appear twice");
            }

            if (i > 0) {
                PointArretRequest previous = stops.get(i - 1);

                if (current.ordre() <= previous.ordre()) {
                    throw new BusinessRuleException("Stop order must increase");
                }

                if (!current.heurePassage().isAfter(previous.heurePassage())) {
                    throw new BusinessRuleException("Stop times must increase");
                }

                if (current.prixDepuisDepart().compareTo(previous.prixDepuisDepart()) <= 0) {
                    throw new BusinessRuleException("Stop prices must increase");
                }
            }
        }
    }






}