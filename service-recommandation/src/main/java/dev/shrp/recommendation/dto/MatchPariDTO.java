package dev.shrp.recommendation.dto;

public class MatchPariDTO {
    private Long id_match;
    private Long id_pari;

    public MatchPariDTO(Long id_match, Long id_pari){
        this.id_match = id_match;
        this.id_pari = id_pari;
    }

    public Long getId_match(){
        return this.id_match;
    }

    public Long getId_pari(){
        return this.id_pari;
    }
}
