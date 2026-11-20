package com.covoiturage.repository;

import com.covoiturage.entity.Trajet;
import com.covoiturage.enums.StatutTrajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.covoiturage.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    @EntityGraph(attributePaths = {"pointsArret", "conducteur", "vehicule"})
    Optional<Trajet> findDetailedById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Trajet t where t.id = :id")
    Optional<Trajet> findByIdForUpdate(@Param("id") Long id);

    boolean existsByVehicule_Id(Long vehiculeId);

    @Query("""
        SELECT DISTINCT t
        FROM Trajet t
        JOIN t.pointsArret depart
        JOIN t.pointsArret arrivee
        WHERE LOWER(depart.ville) = LOWER(:depart)
        AND LOWER(arrivee.ville) = LOWER(:arrivee)
        AND depart.ordre < arrivee.ordre
        AND DATE(depart.heurePassage) = :date
        AND t.statut = :statut
    """)
    List<Trajet> searchTrajets(
            @Param("depart") String depart,
            @Param("arrivee") String arrivee,
            @Param("date") LocalDate date,
            @Param("statut") StatutTrajet statut
    );

    @Query("""
            SELECT DISTINCT t
            FROM Trajet t
            JOIN t.pointsArret depart
            JOIN t.pointsArret arrivee
            JOIN t.pointsArret origine
            WHERE LOWER(depart.ville) = LOWER(:depart)
            AND LOWER(arrivee.ville) = LOWER(:arrivee)
            AND depart.ordre < arrivee.ordre
            AND origine.ordre = 0
            AND origine.heurePassage >= :startDate
            AND origine.heurePassage < :endDate
            AND t.statut = :statut
            AND t.placesProposees - COALESCE(
                (
                    SELECT SUM(r.nombrePlaces)
                    FROM Reservation r
                    WHERE r.trajet = t
                    AND r.statut = :statutReservation
                ), 0
            ) >= :nombrePlaces
                        AND (:prixMax IS NULL OR
                             (arrivee.prixDepuisDepart - depart.prixDepuisDepart) <= :prixMax)
                ORDER BY origine.heurePassage ASC
            """)
    Page<Trajet> searchTrajetsDisponibles(
            @Param("depart") String depart,
            @Param("arrivee") String arrivee,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("statut") StatutTrajet statut,
            @Param("statutReservation") StatutReservation statutReservation,
            @Param("nombrePlaces") int nombrePlaces,
            @Param("prixMax") BigDecimal prixMax,
            Pageable pageable
    );
}
