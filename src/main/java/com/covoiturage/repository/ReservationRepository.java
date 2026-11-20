package com.covoiturage.repository;

import com.covoiturage.entity.Reservation;
import com.covoiturage.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT COALESCE(SUM(r.nombrePlaces), 0)
        FROM Reservation r
        WHERE r.trajet.id = :trajetId
        AND r.statut = :statut
    """)
    long sumReservedSeats(
            @Param("trajetId") Long trajetId,
            @Param("statut") StatutReservation statut
    );

    boolean existsByPassager_IdAndTrajet_IdAndStatut(
            Long passagerId,
            Long trajetId,
            StatutReservation statut
    );

    @EntityGraph(attributePaths = {"passager", "trajet", "pointMontee", "pointDescente"})
    List<Reservation> findByPassager_Id(Long passagerId);

    @EntityGraph(attributePaths = {"passager", "trajet", "pointMontee", "pointDescente"})
    List<Reservation> findByTrajet_IdAndStatut(
            Long trajetId,
            StatutReservation statut
    );
}
