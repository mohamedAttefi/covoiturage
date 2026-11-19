package com.covoiturage.service.impl;

import com.covoiturage.dto.VehiculeRequest;
import com.covoiturage.dto.VehiculeResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.entity.Vehicule;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.VehiculeMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.repository.VehiculeRepository;
import com.covoiturage.service.VehiculeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final ConducteurRepository conducteurRepository;
    private final VehiculeMapper mapper;












}