package dev.shrp.pari.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pari")
public class Pari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pari")
    private Long idPari;

    @Column(name = "somme")
    private String somme;

    @Column(name = "combine")
    private boolean combine;

    @Column(name = "id_parieur")
    private int idParieur;

    @Transient // Pas stocké en BDD, utilisé pour exposer la relation
    private List<Long> idMatchs;

    public Long getId_pari() {
        return idPari;
    }

    public void setId_pari(Long id_pari) {
        this.idPari = id_pari;
    }

    public String getSomme() {
        return somme;
    }

    public void setSomme(String somme) {
        this.somme = somme;
    }

    public boolean isCombine() {
        return combine;
    }

    public void setCombine(boolean combine) {
        this.combine = combine;
    }

    public int getId_parieur() {
        return idParieur;
    }

    public void setId_parieur(int id_parieur) {
        this.idParieur = id_parieur;
    }

    public List<Long> getIdMatchs() {
        return idMatchs;
    }

    public void setIdMatchs(List<Long> idMatchs) {
        this.idMatchs = idMatchs;
    }
}
