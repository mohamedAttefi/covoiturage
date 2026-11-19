package com.covoiturage.controller;

import com.covoiturage.dto.VehiculeRequest;
import com.covoiturage.dto.VehiculeResponse;
import com.covoiturage.service.VehiculeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService service;

    public VehiculeController(VehiculeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponse create(@Valid @RequestBody VehiculeRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<VehiculeResponse> findAll() {
        return service.findAll();
    }






}