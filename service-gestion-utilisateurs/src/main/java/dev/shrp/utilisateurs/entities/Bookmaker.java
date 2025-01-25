package dev.shrp.utilisateurs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bookmaker")
public class Bookmaker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_bookmaker;

    @ManyToOne(optional = false)
    @JoinColumn(name = "compte_id", nullable = false)
    private Compte compte;

    public Long getId_bookmaker() {
        return id_bookmaker;
    }

    public void setId_bookmaker(Long id_bookmaker) {
        this.id_bookmaker = id_bookmaker;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
