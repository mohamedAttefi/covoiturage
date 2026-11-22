package com.covoiturage.service.impl;

import com.covoiturage.dto.ConducteurRequest;
import com.covoiturage.dto.ConducteurResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.mapper.ConducteurMapper;
import com.covoiturage.repository.ConducteurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConducteurServiceImplTest {

    @Mock
    ConducteurRepository repository;

    @Mock
    ConducteurMapper mapper;

    @InjectMocks
    ConducteurServiceImpl service;

    @Test
    void shouldRejectDuplicateEmail() {

        ConducteurRequest request = new ConducteurRequest(
                "Alami", "Ahmed",
                "ahmed@example.com", "0612345678", "PER123"
        );

        when(repository.existsByEmail(request.email()))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> service.create(request)
        );

        verify(repository).existsByEmail(request.email());
    }

    @Test
    void shouldCreateConducteurSuccessfully() {

        ConducteurRequest request = new ConducteurRequest(
                "Alami", "Ahmed",
                "ahmed@example.com", "0612345678", "PER123"
        );

        Conducteur conducteur = new Conducteur();

        Conducteur saved = new Conducteur();

        ConducteurResponse response = new ConducteurResponse(
                1L, "Alami", "Ahmed",
                "ahmed@example.com", "0612345678", "PER123"
        );

        when(repository.existsByEmail(request.email())).thenReturn(false);
        when(repository.existsByNumeroPermis(request.numeroPermis())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(conducteur);
        when(repository.save(conducteur)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ConducteurResponse result = service.create(request);

        assertNotNull(result);
        assertEquals("ahmed@example.com", result.email());

        verify(repository).save(conducteur);
    }


}