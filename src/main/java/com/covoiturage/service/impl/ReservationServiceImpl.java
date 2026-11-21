package com.covoiturage.service.impl;

import com.covoiturage.dto.ReservationRequest;
import com.covoiturage.dto.ReservationResponse;
import com.covoiturage.entity.*;
import com.covoiturage.enums.*;
import com.covoiturage.exception.*;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.*;
import com.covoiturage.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;
    private final TrajetRepository trajetRepository;
    private final ReservationMapper mapper;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            PassagerRepository passagerRepository,
            TrajetRepository trajetRepository,
            ReservationMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.passagerRepository = passagerRepository;
        this.trajetRepository = trajetRepository;
        this.mapper = mapper;
    }








}