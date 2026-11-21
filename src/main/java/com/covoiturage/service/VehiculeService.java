package com.covoiturage.service;

import com.covoiturage.dto.VehiculeRequest;
import com.covoiturage.dto.VehiculeResponse;
import java.util.List;

public interface VehiculeService {

    VehiculeResponse create(VehiculeRequest request);

    List<VehiculeResponse> findAll();

    VehiculeResponse findById(Long id);

    VehiculeResponse update(Long id, VehiculeRequest request);


}