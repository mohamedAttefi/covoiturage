package com.covoiturage.repository;

import com.covoiturage.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    boolean existsByEmail(String email);
    boolean existsByNumeroPermis(String numeroPermis);
    List<Conducteur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nom, String prenom, String email
    );
}