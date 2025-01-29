package dev.shrp.recommendation.dto;

public class PariDTO {

    private Long id_parieur;
    private float montant;

    public PariDTO(Long id_parieur, float montant){
        this.id_parieur = id_parieur;
        this.montant = montant;
    }

    public Long getId_parieur(){
        return this.id_parieur;
    }

    public float getMontant(){
        return this.montant;
    }

}
