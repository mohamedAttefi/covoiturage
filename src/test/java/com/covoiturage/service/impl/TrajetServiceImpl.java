package com.covoiturage.service.impl;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.entity.Vehicule;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrajetServiceImplTest {

    @Mock TrajetRepository trajetRepository;
    @Mock ConducteurRepository conducteurRepository;
    @Mock VehiculeRepository vehiculeRepository;
    @Mock TrajetMapper mapper;

    @InjectMocks
    TrajetServiceImpl service;




}