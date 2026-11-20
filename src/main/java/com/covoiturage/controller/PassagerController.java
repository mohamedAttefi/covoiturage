package com.covoiturage.controller;

import com.covoiturage.dto.PassagerRequest;
import com.covoiturage.dto.PassagerResponse;
import com.covoiturage.service.PassagerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
public class PassagerController {

    private final PassagerService service;

    public PassagerController(PassagerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassagerResponse create(@Valid @RequestBody PassagerRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<PassagerResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PassagerResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<PassagerResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @PutMapping("/{id}")
    public PassagerResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PassagerRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}