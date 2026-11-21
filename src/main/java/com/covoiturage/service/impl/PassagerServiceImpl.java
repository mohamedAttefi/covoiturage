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

    @Override
    public PassagerResponse create(PassagerRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Passager passager = mapper.toEntity(request);

        return mapper.toResponse(repository.save(passager));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassagerResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerResponse findById(Long id) {
        Passager passager = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passager introuvable"));

        return mapper.toResponse(passager);
    }

    @Override
    public PassagerResponse update(Long id, PassagerRequest request) {

        Passager passager = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passager introuvable"));

        if (!passager.getEmail().equals(request.email())
                && repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        passager.setNom(request.nom());
        passager.setPrenom(request.prenom());
        passager.setEmail(request.email());
        passager.setTelephone(request.telephone());

        return mapper.toResponse(repository.save(passager));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Passager introuvable");
        }

        repository.deleteById(id);
    }
}