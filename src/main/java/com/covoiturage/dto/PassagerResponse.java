package com.covoiturage.dto;

public record PassagerResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String telephone
) {}