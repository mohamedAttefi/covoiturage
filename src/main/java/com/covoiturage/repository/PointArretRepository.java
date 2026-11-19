package com.covoiturage.repository;

import com.covoiturage.entity.PointArret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointArretRepository extends JpaRepository<PointArret, Long> {
}
