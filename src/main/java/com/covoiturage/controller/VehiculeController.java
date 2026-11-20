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

    @GetMapping("/{id}")
    public VehiculeResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public VehiculeResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VehiculeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}