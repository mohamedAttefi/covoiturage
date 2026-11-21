package com.covoiturage.service.impl;

import com.covoiturage.dto.PassagerRequest;
import com.covoiturage.dto.PassagerResponse;
import com.covoiturage.entity.Passager;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.PassagerMapper;
import com.covoiturage.repository.PassagerRepository;
import com.covoiturage.service.PassagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository repository;
    private final PassagerMapper mapper;

    public PassagerServiceImpl(
            PassagerRepository repository,
            PassagerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }










}