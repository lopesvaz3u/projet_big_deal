package dev.shrp.match.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "match")
public class Match {
    @Id
    @Column(name = "id_match")
    private Long id_match;

    @Column(name = "cote_equipe_domicile")
    private Float cote_equipe_domicile = 2.0f;

    @Column(name = "cote_equipe_exterieur")
    private Float cote_equipe_exterieur = 3.0f;

    @Column(name = "cote_nul")
    private Float cote_nul = 4.0f;

    @Column(name = "depart_prevu")
    @Temporal(TemporalType.TIMESTAMP)
    private Date depart_prevu;

    @Column(name = "depart_reel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date depart_reel;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EEtatMatch etat;

    @Column(name = "id_equipe_domicile")
    private Long equipe_1;

    @Column(name = "id_equipe_exterieur")
    private Long equipe_2;

    @Column(name = "score_equipe_domicile",  columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_1;

    @Column(name = "score_equipe_exterieur", columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_2;

    @Column(name = "id_competition")
    private Long competition;

    public enum EEtatMatch {
        Prévu,
        En_cours,
        Terminé,
        Annulé
    }

    public Float getCote_equipe_domicile() {
        return cote_equipe_domicile;
    }

    public void setCote_equipe_domicile(Float cote_equipe_domicile) {
        this.cote_equipe_domicile = cote_equipe_domicile;
    }

    public Float getCote_equipe_exterieur() {
        return cote_equipe_exterieur;
    }

    public void setCote_equipe_exterieur(Float cote_equipe_exterieur) {
        this.cote_equipe_exterieur = cote_equipe_exterieur;
    }

    public Float getCote_nul() {
        return cote_nul;
    }

    public void setCote_nul(Float cote_nul) {
        this.cote_nul = cote_nul;
    }

    public Long getCompetition() {
        return competition;
    }

    public void setCompetition(Long competition) {
        this.competition = competition;
    }

    public Integer getScore_equipe_1() {
        return score_equipe_1;
    }

    public void setScore_equipe_1(Integer score_equipe_1) {
        this.score_equipe_1 = score_equipe_1;
    }

    public Integer getScore_equipe_2() {
        return score_equipe_2;
    }

    public void setScore_equipe_2(Integer score_equipe_2) {
        this.score_equipe_2 = score_equipe_2;
    }

    public Date getDepart_reel() {
        return depart_reel;
    }

    public void setDepart_reel(Date depart_reel) {
        this.depart_reel = depart_reel;
    }

    public Date getDepart_prevu() {
        return depart_prevu;
    }

    public void setDepart_prevu(Date depart_prevu) {
        this.depart_prevu = depart_prevu;
    }

    public EEtatMatch getEtat() {
        return etat;
    }

    public void setEtat(EEtatMatch etat) {
        this.etat = etat;
    }

    public Long getId_match() {
        return id_match;
    }

    public void setId_match(Long id_match) {
        this.id_match = id_match;
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
}