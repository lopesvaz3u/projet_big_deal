package dev.shrp.recommendation.dto;

public class PariDTO {
    private long id_parieur;
    private float montant;
    private long id_pari;

    public PariDTO(Long id_pari, Long id_parieur, float montant){
        this.id_pari = id_pari;
        this.id_parieur = id_parieur;
        this.montant = montant;
    }

    public Long getId_parieur(){
        return this.id_parieur;
    }

    public float getMontant(){
        return this.montant;
    }

    public float getId_pari() { return this.id_pari; }

}
