package com.covoiturage.service.impl;

import com.covoiturage.dto.PassagerRequest;
import com.covoiturage.dto.PassagerResponse;
import com.covoiturage.entity.Passager;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.mapper.PassagerMapper;
import com.covoiturage.repository.PassagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassagerServiceImplTest {

    @Mock
    PassagerRepository repository;

    @Mock
    PassagerMapper mapper;

    @InjectMocks
    PassagerServiceImpl service;

    @Test
    void shouldRejectDuplicateEmail() {

        PassagerRequest request = new PassagerRequest(
                "Karim", "Ali",
                "karim@example.com", "0612345678"
        );

        when(repository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> service.create(request)
        );

        verify(repository, never()).save(any());
    }

    @Test
    void shouldCreatePassagerSuccessfully() {

        PassagerRequest request = new PassagerRequest(
                "Karim", "Ali",
                "karim@example.com", "0612345678"
        );

        Passager passager = new Passager();

        PassagerResponse response = new PassagerResponse(
                1L, "Karim", "Ali",
                "karim@example.com", "0612345678"
        );

        when(repository.existsByEmail(request.email())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(passager);
        when(repository.save(passager)).thenReturn(passager);
        when(mapper.toResponse(passager)).thenReturn(response);

        PassagerResponse result = service.create(request);

        assertNotNull(result);
        assertEquals("karim@example.com", result.email());

        verify(repository).save(passager);
    }


}