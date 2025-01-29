package dev.shrp.recommendation.dto;

public class RecommendationDTO {
    private Long idParieur;
    private Long idMatch;
    private float montant;
    private double pourcentageJustesse;

    // Constructeur
    public RecommendationDTO(Long idParieur, Long idMatch, float montant, double pourcentageJustesse) {
        this.idParieur = idParieur;
        this.idMatch = idMatch;
        this.montant = montant;
        this.pourcentageJustesse = pourcentageJustesse;
    }

    // Getters et setters
    public Long getIdParieur() {
        return idParieur;
    }

    public void setIdParieur(Long idParieur) {
        this.idParieur = idParieur;
    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public double getPourcentageJustesse() {
        return pourcentageJustesse;
    }

    public void setPourcentageJustesse(double pourcentageJustesse) {
        this.pourcentageJustesse = pourcentageJustesse;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}