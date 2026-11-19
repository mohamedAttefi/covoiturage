package com.covoiturage.mapper;

import com.covoiturage.dto.PassagerRequest;
import com.covoiturage.dto.PassagerResponse;
import com.covoiturage.entity.Passager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassagerMapper {

    Passager toEntity(PassagerRequest request);

    PassagerResponse toResponse(Passager passager);
}