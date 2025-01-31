package dev.shrp.pari.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "pari")
public class Pari {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pari")
    private Long idPari;

    @Column(name = "mise")
    private double mise;

    @Column(name = "combine")
    private boolean combine;

    @Column(name = "id_parieur")
    private Long idParieur;

    public Long getId_pari() {
        return idPari;
    }

    public void setId_pari(Long id_pari) {
        this.idPari = id_pari;
    }

    public Double getMise() {
        return mise;
    }

    public void setMise(Double mise) {
        this.mise = mise;
    }

    public boolean isCombine() {
        return combine;
    }

    public void setCombine(boolean combine) {
        this.combine = combine;
    }

    public Long getId_parieur() {
        return idParieur;
    }

    public void setId_parieur(long id_parieur) {
        this.idParieur = id_parieur;
    }
}
