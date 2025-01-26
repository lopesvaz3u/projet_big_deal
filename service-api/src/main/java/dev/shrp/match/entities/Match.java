package dev.shrp.match.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_match")
    private Long id_match;

    @Column(name = "cote", nullable = false)
    private Float cote;

    @Column(name = "depart_prevu", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date depart_prevu;

    @Column(name = "depart_reel")
    @Temporal(TemporalType.DATE)
    private Date depart_reel;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EEtatMatch etat;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_equipe_1", nullable = false)
    //@Column(name = "id_equipe_1", nullable = false)
    private Equipe equipe_1;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_equipe_2", nullable = false)
    //@Column(name = "id_equipe_2", nullable = false)
    private Equipe equipe_2;

    @Column(name = "score_equipe_1", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_1;

    @Column(name = "score_equipe_2", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_2;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_competition")
    //@Column(name = "competition", nullable = false)
    private Competition competition;

    public enum EEtatMatch {
        Prévu,
        En_cours,
        Terminé,
        Annulé
    }



    public Float getCote() {
        return cote;
    }

    public void setCote(Float cote) {
        this.cote = cote;
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

    public void setId_equipe_1(Equipe equipe_1) {
        this.equipe_1 = equipe_1;
    }

    public void setId_equipe_2(Equipe equipe_2) {
        this.equipe_2 = equipe_2;
    }


}