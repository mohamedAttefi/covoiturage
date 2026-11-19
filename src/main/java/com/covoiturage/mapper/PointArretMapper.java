package com.covoiturage.mapper;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.PointArretResponse;
import com.covoiturage.entity.PointArret;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointArretMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajet", ignore = true)
    PointArret toEntity(PointArretRequest request);

    PointArretResponse toResponse(PointArret pointArret);
}