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

    @JoinColumn(name = "id_equipe_1", nullable = false)
    private Long id_equipe_1;

    @JoinColumn(name = "id_equipe_2", nullable = false)
    private Long id_equipe_2;

    @Column(name = "score_equipe_1", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_1;

    @Column(name = "score_equipe_2", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer score_equipe_2;

    @JoinColumn(name = "id_competition")
    private Long id_competition;

    public enum EEtatMatch {
        Prévu,
        En_cours,
        Terminé,
        Annulé
    }
}
