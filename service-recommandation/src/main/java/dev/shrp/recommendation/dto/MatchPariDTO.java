package dev.shrp.recommendation.dto;

public class MatchPariDTO {
    private Long id_match;
    private Long id_parieur;

    public MatchPariDTO(Long id_match, Long id_parieur){
        this.id_match = id_match;
        this.id_parieur = id_parieur;
    }

    public Long getId_match(){
        return this.id_match;
    }

    public Long getId_parieur(){
        return this.id_parieur;
    }
}
