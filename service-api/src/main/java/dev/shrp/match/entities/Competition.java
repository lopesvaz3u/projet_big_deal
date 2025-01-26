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

    @Version
    private int version;  // Champ version pour le verrouillage optimiste


    public Long getId_competition() {
        return id_competition;
    }  

    public String getNom_competition() {
        return nom_competition;
    }  

    public void setId_competition(Long id_competition) {
        this.id_competition = id_competition;
    }
    
    public void setNom_competition(String nom_competition) {
        this.nom_competition = nom_competition;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
