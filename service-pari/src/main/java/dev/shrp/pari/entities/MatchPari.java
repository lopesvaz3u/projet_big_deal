package dev.shrp.pari.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "match_pari")
public class MatchPari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_match", nullable = false)
    private Long idMatch; // ID du match

    @Column(name = "id_pari", nullable = false)
    private Long idPari; // ID du pari

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public Long getIdPari() {
        return idPari;
    }

    public void setIdPari(Long idPari) {
        this.idPari = idPari;
    }
}
