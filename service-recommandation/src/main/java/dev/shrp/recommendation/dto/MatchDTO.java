package dev.shrp.recommendation.dto;

public class MatchDTO {
    private Long id_match;
    private Float cote;
    private String etat; // Utilisation d'un String pour représenter l'enum
    private Long equipe_1;
    private Long equipe_2;
    private Long competition;

    public MatchDTO(Long id_match, Float cote, String etat, Long equipe_1, Long equipe_2, Long competition){
        this.id_match = id_match;
        this.cote = cote;
        this.etat = etat;
        this.equipe_1 = equipe_1;
        this.equipe_2 = equipe_2;
        this.competition = competition;
    }

    // Getters et setters
    public Long getId_match() {
        return id_match;
    }

    public void setId_match(Long id_match) {
        this.id_match = id_match;
    }

    public Float getCote() {
        return cote;
    }

    public void setCote(Float cote) {
        this.cote = cote;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Long getEquipe_1() {
        return equipe_1;
    }

    public void setEquipe_1(Long equipe_1) {
        this.equipe_1 = equipe_1;
    }

    public Long getEquipe_2() {
        return equipe_2;
    }

    public void setEquipe_2(Long equipe_2) {
        this.equipe_2 = equipe_2;
    }

    public Long getCompetition() {
        return competition;
    }

    public void setCompetition(Long competition) {
        this.competition = competition;
    }
}