package com.covoiturage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotNull
        Long passagerId,

        @NotNull
        Long trajetId,

        @NotNull
        Long pointMonteeId,

        @NotNull
        Long pointDescenteId,

        @Min(1)
        int nombrePlaces

) {}