package com.covoiturage.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TrajetRequest(

        @NotNull
        Long conducteurId,

        @NotNull
        Long vehiculeId,

        @Min(1)
        int placesProposees,

        @NotEmpty
        List<@Valid PointArretRequest> pointsArret

) {}