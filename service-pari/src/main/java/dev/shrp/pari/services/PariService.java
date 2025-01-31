package dev.shrp.pari.services;

import dev.shrp.pari.entities.MatchPari;
import dev.shrp.pari.entities.Pari;
import dev.shrp.pari.repositories.MatchPariRepository;
import dev.shrp.pari.repositories.PariRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PariService {

    @Autowired
    private PariRepository pariRepository;

    @Autowired
    private MatchPariRepository matchPariRepository;

    public List<Pari> getAllParis() {
        return pariRepository.findAll();
    }

    public Optional<Pari> getOnePariById(Long idPari) {
        return pariRepository.findById(idPari);
    }

    public List<Pari> getAllPariOfParieur(Long idParieur) {
        return pariRepository.findByIdParieur(idParieur);
    }

    public Pari createPariSimple(Long matchId, Long parieurId, String resultat, Double mise) {
        // Vérifier si l'id parieur existe
        // Vérifier si l'id match existe
        // Vérifier si le match est encore jouable
        // Vérifier si le parieur a assez d'argent
        // Vérifier si le parieur n'a pas déjà parié sur ce match
        Pari pari = new Pari();
        pari.setId_parieur(parieurId);
        pari.setMise(mise);
        pari.setCombine(false);

        pari = pariRepository.save(pari);

        MatchPari matchPari = new MatchPari();
        matchPari.setIdMatch(matchId);
        matchPari.setIdPari(pari.getId_pari());
        matchPari.setResultatParie(resultat);

        matchPariRepository.save(matchPari);

        return pari;
    }

}
