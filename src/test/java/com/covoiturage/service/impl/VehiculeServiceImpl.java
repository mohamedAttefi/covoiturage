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

    @Test
    void shouldRejectDuplicateImmatriculation() {

        VehiculeRequest request = new VehiculeRequest(
                "Toyota", "Corolla",
                "12345-A-6", "White", 5, 1L
        );

        when(vehiculeRepository.existsByImmatriculation(
                request.immatriculation()
        )).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> service.create(request)
        );

        verify(vehiculeRepository, never()).save(any());
    }

    @Test
    void shouldRejectVehicleWhenDriverDoesNotExist() {

        VehiculeRequest request = new VehiculeRequest(
                "Toyota", "Corolla",
                "12345-A-6", "White", 5, 99L
        );

        when(vehiculeRepository.existsByImmatriculation(
                request.immatriculation()
        )).thenReturn(false);

        when(conducteurRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.create(request)
        );

        verify(vehiculeRepository, never()).save(any());
    }
}