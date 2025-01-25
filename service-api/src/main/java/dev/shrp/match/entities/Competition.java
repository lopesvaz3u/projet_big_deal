package dev.shrp.match.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competition")
    private Long id_competition;

    @Column(name = "nom_competition", nullable = false)
    private String nom_competition;
}
