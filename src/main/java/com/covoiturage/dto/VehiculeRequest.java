package com.covoiturage.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehiculeRequest(

        @NotBlank
        String marque,

        @NotBlank
        String modele,

        @NotBlank
        String immatriculation,

        String couleur,

        @Min(1)
        @Max(8)
        int capacite,

        @NotNull
        Long conducteurId

) {}