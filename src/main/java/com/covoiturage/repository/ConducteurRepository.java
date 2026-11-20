package com.covoiturage.repository;

import com.covoiturage.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    boolean existsByEmail(String email);
    boolean existsByNumeroPermis(String numeroPermis);
}