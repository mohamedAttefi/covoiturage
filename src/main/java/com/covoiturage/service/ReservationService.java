package com.covoiturage.service;

import com.covoiturage.dto.ReservationRequest;
import com.covoiturage.dto.ReservationResponse;
import java.util.List;

public interface ReservationService {

    ReservationResponse create(ReservationRequest request);

    List<ReservationResponse> findAll();

    ReservationResponse findById(Long id);

    void cancel(Long id);
}