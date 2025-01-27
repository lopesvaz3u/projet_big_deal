package dev.shrp.match.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "equipe")
public class Equipe {
    @Id
    @Column(name = "id_equipe")
    private Long id_equipe;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "pays", nullable = false)
    private String pays;

    public void setId_equipe(Long id_equipe) {
        this.id_equipe = id_equipe;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPays(String pays) {
        this.pays = pays;
    }

    public Long getId_equipe() {
        return id_equipe;
    }    
    
    public String getNom() {
        return nom;
    }
}