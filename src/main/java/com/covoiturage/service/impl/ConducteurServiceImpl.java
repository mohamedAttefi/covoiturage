package com.covoiturage.service.impl;

import com.covoiturage.dto.ConducteurRequest;
import com.covoiturage.dto.ConducteurResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.ConducteurMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.service.ConducteurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConducteurServiceImpl implements ConducteurService {

    private final ConducteurRepository repository;
    private final ConducteurMapper mapper;












}