package dev.shrp.utilisateurs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "parieur")
public class Parieur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_parieur;

    @Column(nullable = false)
    private String rib;

    @Column(nullable = false)
    private double argent = 5;

    @ManyToOne(optional = false)
    @JoinColumn(name = "compte_id", nullable = false)
    private Compte compte;

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Long getId_parieur() {
        return id_parieur;
    }

    public void setId_parieur(Long id_parieur) {
        this.id_parieur = id_parieur;
    }

    public double getArgent() {
        return argent;
    }

    public void setArgent(double argent) {
        this.argent = argent;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
