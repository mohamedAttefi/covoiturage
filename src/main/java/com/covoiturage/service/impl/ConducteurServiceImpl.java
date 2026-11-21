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

    public ConducteurServiceImpl(
            ConducteurRepository repository,
            ConducteurMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ConducteurResponse create(ConducteurRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (repository.existsByNumeroPermis(request.numeroPermis())) {
            throw new DuplicateResourceException("Licence number already exists");
        }

        Conducteur conducteur = mapper.toEntity(request);

        return mapper.toResponse(repository.save(conducteur));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConducteurResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConducteurResponse findById(Long id) {
        Conducteur conducteur = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur introuvable"));

        return mapper.toResponse(conducteur);
    }

    @Override
    public ConducteurResponse update(Long id, ConducteurRequest request) {

        Conducteur conducteur = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur introuvable"));

        if (!conducteur.getEmail().equals(request.email())
                && repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (!conducteur.getNumeroPermis().equals(request.numeroPermis())
                && repository.existsByNumeroPermis(request.numeroPermis())) {
            throw new DuplicateResourceException("Licence number already exists");
        }

        conducteur.setNom(request.nom());
        conducteur.setPrenom(request.prenom());
        conducteur.setEmail(request.email());
        conducteur.setTelephone(request.telephone());
        conducteur.setNumeroPermis(request.numeroPermis());

        return mapper.toResponse(repository.save(conducteur));
    }


}