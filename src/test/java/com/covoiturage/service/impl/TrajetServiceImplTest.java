package com.covoiturage.service.impl;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.entity.Vehicule;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrajetServiceImplTest {

    @Mock TrajetRepository trajetRepository;
    @Mock ConducteurRepository conducteurRepository;
    @Mock VehiculeRepository vehiculeRepository;
    @Mock TrajetMapper mapper;

    @InjectMocks
    TrajetServiceImpl service;

    @Test
    void shouldRejectJourneyWithLessThanTwoStops() {

        TrajetRequest request = new TrajetRequest(
                1L, 1L, 3, List.of()
        );

        when(conducteurRepository.findById(1L))
                .thenReturn(Optional.of(new Conducteur()));

        Vehicule vehicule = new Vehicule();
        Conducteur conducteur = new Conducteur();

        conducteur.setId(1L);
        vehicule.setConducteur(conducteur);

        when(vehiculeRepository.findById(1L))
                .thenReturn(Optional.of(vehicule));

        assertThrows(
                BusinessRuleException.class,
                () -> service.create(request)
        );

        verify(trajetRepository, never()).save(any());
    }

    @Test
    void shouldRejectPastDepartureDate() {

        PointArretRequest departure = new PointArretRequest(
                "Marrakech",
                0,
                LocalDateTime.now().minusDays(1),
                BigDecimal.ZERO
        );

        PointArretRequest arrival = new PointArretRequest(
                "Casablanca",
                1,
                LocalDateTime.now().plusDays(1),
                new BigDecimal("120")
        );

        TrajetRequest request = new TrajetRequest(
                1L, 1L, 3, List.of(departure, arrival)
        );

        Conducteur conducteur = new Conducteur();
        conducteur.setId(1L);

        Vehicule vehicule = new Vehicule();
        vehicule.setConducteur(conducteur);

        when(conducteurRepository.findById(1L))
                .thenReturn(Optional.of(conducteur));

        when(vehiculeRepository.findById(1L))
                .thenReturn(Optional.of(vehicule));

        assertThrows(
                BusinessRuleException.class,
                () -> service.create(request)
        );

        verify(trajetRepository, never()).save(any());
    }
}