package dev.shrp.utilisateurs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comptes2")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true, unique = true)
    private String identifiant;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = true)
    private String nom;

    @Column(nullable = true)
    private String prenom;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telephone;

    @Column(nullable = true)
    private String adresse;
}
