package com.covoiturage.service.impl;

import com.covoiturage.entity.Trajet;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.covoiturage.entity.Passager;
import com.covoiturage.dto.ReservationRequest;
import com.covoiturage.enums.StatutReservation;
import java.util.Optional;
import com.covoiturage.entity.PointArret;
import com.covoiturage.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock ReservationRepository reservationRepository;
    @Mock PassagerRepository passagerRepository;
    @Mock TrajetRepository trajetRepository;
    @Mock ReservationMapper mapper;

    @InjectMocks
    ReservationServiceImpl service;

    @Test
    void shouldRejectReservationForUnavailableJourney() {

        Trajet trajet = new Trajet();
        trajet.setStatut(StatutTrajet.ANNULE);

        when(passagerRepository.findById(1L))
                .thenReturn(Optional.of(new com.covoiturage.entity.Passager()));

        when(trajetRepository.findById(1L))
                .thenReturn(Optional.of(trajet));

        var request = new com.covoiturage.dto.ReservationRequest(
                1L, 1L, 1L, 2L, 1
        );

        assertThrows(
                BusinessRuleException.class,
                () -> service.create(request)
        );

        verify(reservationRepository, never()).save(any());
    }




}