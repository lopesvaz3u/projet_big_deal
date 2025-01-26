package dev.shrp.utilisateurs.services;

import dev.shrp.utilisateurs.entities.Bookmaker;
import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.entities.Parieur;
import dev.shrp.utilisateurs.repositories.BookmakerRepository;
import dev.shrp.utilisateurs.repositories.CompteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmakerService {

    @Autowired
    private BookmakerRepository bookmakerRepository;

    @Autowired
    private CompteRepository compteRepository;

    public List<Bookmaker> getAllBookmakers() {
        return bookmakerRepository.findAll();
    }

    public Optional<Bookmaker> getOneBookmaerById(Long id) {
        return bookmakerRepository.findById(id);
    }

    @Transactional
    public Bookmaker createBookmaker(Compte compte) {
        if (compte == null) {
            throw new IllegalArgumentException("Le compte ne peut pas être null.");
        }
        if (compte.getId() == null || !compteRepository.existsById(compte.getId())) {
            compte = compteRepository.save(compte);

            Bookmaker bookmaker = new Bookmaker();
            bookmaker.setCompte(compte);
            try {
                return bookmakerRepository.save(bookmaker);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la création du bookmaker : " + e.getMessage(), e);
            }
        }else {
            throw new IllegalArgumentException("Le compte existe déjà.");
        }
    }

}
