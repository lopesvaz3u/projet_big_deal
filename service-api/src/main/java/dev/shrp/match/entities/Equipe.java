package dev.shrp.match.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "equipe")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Long id_equipe;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "pays", nullable = false)
    private String pays;
}