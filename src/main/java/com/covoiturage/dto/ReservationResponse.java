package com.covoiturage.dto;

import com.covoiturage.enums.StatutReservation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        Long passagerId,
        Long trajetId,
        Long pointMonteeId,
        Long pointDescenteId,
        int nombrePlaces,
        BigDecimal montantTotal,
        LocalDateTime dateReservation,
        StatutReservation statut
) {}