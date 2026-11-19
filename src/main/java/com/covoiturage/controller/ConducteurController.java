package com.covoiturage.controller;

import com.covoiturage.dto.ConducteurRequest;
import com.covoiturage.dto.ConducteurResponse;
import com.covoiturage.service.ConducteurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conducteurs")
public class ConducteurController {

    private final ConducteurService service;

    public ConducteurController(ConducteurService service) {
        this.service = service;
    }










}