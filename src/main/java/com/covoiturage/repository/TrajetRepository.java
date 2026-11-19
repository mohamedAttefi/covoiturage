package com.covoiturage.repository;

import com.covoiturage.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {
}
