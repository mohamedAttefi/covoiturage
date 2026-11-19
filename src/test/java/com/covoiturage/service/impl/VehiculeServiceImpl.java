package com.covoiturage.service.impl;

import com.covoiturage.dto.VehiculeRequest;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.VehiculeMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.repository.VehiculeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceImplTest {

    @Mock
    VehiculeRepository vehiculeRepository;

    @Mock
    ConducteurRepository conducteurRepository;

    @Mock
    VehiculeMapper mapper;

    @InjectMocks
    VehiculeServiceImpl service;




}