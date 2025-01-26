package dev.shrp.pari.services;

import dev.shrp.pari.entities.Pari;
import dev.shrp.pari.repositories.PariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PariService {

    @Autowired
    private PariRepository pariRepository;

    public List<Pari> getAllParis() {
        return pariRepository.findAll();
    }

    public Optional<Pari> getOnePariById(Long idPari) {
        return pariRepository.findById(idPari);
    }

    public List<Pari> getAllPariOfParieur(Long idParieur) {
        return pariRepository.findByIdParieur(idParieur);
    }

}
