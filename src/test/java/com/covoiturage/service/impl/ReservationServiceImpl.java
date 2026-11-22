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

    @Test
    void shouldRejectReservationWhenNotEnoughSeats() {

        Passager passager = new Passager();
        passager.setId(1L);

        Trajet trajet = new Trajet();
        trajet.setId(1L);
        trajet.setStatut(StatutTrajet.PUBLIE);
        trajet.setPlacesProposees(3);

        when(passagerRepository.findById(1L))
                .thenReturn(Optional.of(passager));

        when(trajetRepository.findById(1L))
                .thenReturn(Optional.of(trajet));

        when(reservationRepository.existsByPassager_IdAndTrajet_IdAndStatut(
                1L, 1L, StatutReservation.CONFIRMEE
        )).thenReturn(false);

        when(reservationRepository.sumReservedSeats(
                1L, StatutReservation.CONFIRMEE
        )).thenReturn(2L);

        var request = new ReservationRequest(1L, 1L, 10L, 11L, 2);

        assertThrows(
                BusinessRuleException.class,
                () -> service.create(request)
        );

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void shouldRejectCancellationWithin24Hours() {

        PointArret departure = new PointArret();
        departure.setHeurePassage(LocalDateTime.now().plusHours(12));

        Trajet trajet = new Trajet();
        trajet.setPointsArret(List.of(departure));

        Reservation reservation = new Reservation();
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setTrajet(trajet);

        when(reservationRepository.findById(1L))
                .thenReturn(Optional.of(reservation));

        assertThrows(
                BusinessRuleException.class,
                () -> service.cancel(1L)
        );

        assertEquals(
                StatutReservation.CONFIRMEE,
                reservation.getStatut()
        );
    }
}