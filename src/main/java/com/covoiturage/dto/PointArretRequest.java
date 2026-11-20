package com.covoiturage.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PointArretRequest(

        @NotBlank
        String ville,

        @Min(0)
        int ordre,

        @NotNull
        LocalDateTime heurePassage,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal prixDepuisDepart

) {}
