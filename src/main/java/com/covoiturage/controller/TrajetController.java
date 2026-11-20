package com.covoiturage.controller;

import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import com.covoiturage.service.TrajetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.math.BigDecimal;

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

    @GetMapping("/search")
    public Page<TrajetResponse> search(
            @RequestParam String depart,
            @RequestParam String arrivee,
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "1") int nombrePlaces,
            @RequestParam(required = false) BigDecimal prixMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.unsorted()
        );

        return service.search(
                depart,
                arrivee,
                date,
                nombrePlaces,
                prixMax,
                pageable
        );
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id, @RequestParam Long conducteurId) {
        service.cancel(id, conducteurId);
    }

    @PatchMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable Long id, @RequestParam Long conducteurId) {
        service.finish(id, conducteurId);
    }

    @GetMapping("/{id}/reservations")
    public List<com.covoiturage.dto.ReservationResponse> reservations(
            @PathVariable Long id, @RequestParam Long conducteurId) {
        return service.reservations(id, conducteurId);
    }
}
