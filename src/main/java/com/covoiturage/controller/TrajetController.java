package com.covoiturage.controller;

import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import com.covoiturage.service.TrajetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trajets")
public class TrajetController {

    private final TrajetService service;

    public TrajetController(TrajetService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrajetResponse create(@Valid @RequestBody TrajetRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TrajetResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TrajetResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }


}