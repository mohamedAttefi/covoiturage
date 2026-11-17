package com.covoiturage.entity;

import com.covoiturage.enums.StatutTrajet;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trajets")
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Conducteur conducteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;

    @Column(nullable = false)
    private int placesProposees;

    @Enumerated(EnumType.STRING)
    private StatutTrajet statut = StatutTrajet.PUBLIE;

    @OneToMany(mappedBy = "trajet",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("ordre ASC")
    private List<PointArret> pointsArret = new ArrayList<>();

    public Trajet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conducteur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Conducteur conducteur) {
        this.conducteur = conducteur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public int getPlacesProposees() {
        return placesProposees;
    }

    public void setPlacesProposees(int placesProposees) {
        this.placesProposees = placesProposees;
    }

    public StatutTrajet getStatut() {
        return statut;
    }

    public void setStatut(StatutTrajet statut) {
        this.statut = statut;
    }

    public List<PointArret> getPointsArret() {
        return pointsArret;
    }

    public void setPointsArret(List<PointArret> pointsArret) {
        this.pointsArret = pointsArret;
    }
}
