package com.covoiturage.repository;

import com.covoiturage.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    boolean existsByImmatriculation(String immatriculation);
}
