package com.covoiturage.service.impl;

import com.covoiturage.dto.PointArretRequest;
import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import com.covoiturage.entity.*;
import com.covoiturage.enums.StatutTrajet;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.*;
import com.covoiturage.service.TrajetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class  TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final ConducteurRepository conducteurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final TrajetMapper mapper;












}