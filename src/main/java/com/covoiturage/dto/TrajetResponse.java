package com.covoiturage.dto;

import com.covoiturage.enums.StatutTrajet;
import java.util.List;

public record TrajetResponse(
        Long id,
        Long conducteurId,
        Long vehiculeId,
        int placesProposees,
        StatutTrajet statut,
        List<PointArretResponse> pointsArret
) {}