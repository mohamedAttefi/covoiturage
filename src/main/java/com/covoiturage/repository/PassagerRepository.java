package com.covoiturage.repository;

import com.covoiturage.entity.Passager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassagerRepository extends JpaRepository<Passager, Long> {
    boolean existsByEmail(String email);
    List<Passager> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nom, String prenom, String email
    );
}
