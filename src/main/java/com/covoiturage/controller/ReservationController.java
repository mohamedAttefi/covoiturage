package com.covoiturage.controller;

import com.covoiturage.dto.ReservationRequest;
import com.covoiturage.dto.ReservationResponse;
import com.covoiturage.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return service.create(request);
    }

    @GetMapping("/passager/{passagerId}")
    public List<ReservationResponse> findByPassagerId(
            @PathVariable Long passagerId) {
        return service.findByPassagerId(passagerId);
    }

    @PatchMapping("/{id}/annuler")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id, @RequestParam Long passagerId) {
        service.cancel(id, passagerId);
    }
}
