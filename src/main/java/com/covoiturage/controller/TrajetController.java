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










}