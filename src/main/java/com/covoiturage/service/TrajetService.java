package com.covoiturage.service;

import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;

public interface TrajetService {

    TrajetResponse create(TrajetRequest request);

    List<TrajetResponse> findAll();

    TrajetResponse findById(Long id);

    Page<TrajetResponse> search(
            String depart,
            String arrivee,
            LocalDate date,
            int nombrePlaces,
            BigDecimal prixMax,
            Pageable pageable
    );

    List<com.covoiturage.dto.ReservationResponse> reservations(Long id, Long conducteurId);

    void cancel(Long id, Long conducteurId);

    void finish(Long id, Long conducteurId);

}
