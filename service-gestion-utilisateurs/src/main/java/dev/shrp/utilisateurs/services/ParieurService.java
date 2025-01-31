package dev.shrp.utilisateurs.services;

import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.entities.Parieur;
import dev.shrp.utilisateurs.repositories.CompteRepository;
import dev.shrp.utilisateurs.repositories.ParieurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParieurService {

    @Autowired
    private ParieurRepository parieurRepository;

    @Autowired
    private CompteRepository compteRepository;

    public List<Parieur> getAllParieurs() {
        return parieurRepository.findAll();
    }

    public Optional<Parieur> getOneParieurById(Long id) {
        return parieurRepository.findById(id);
    }

    @Transactional
    public Parieur createParieur(Compte compte, String rib) {
        if (compte == null) {
            throw new IllegalArgumentException("Le compte ne peut pas être null.");
        }
        if (rib == null || rib.isEmpty()) {
            throw new IllegalArgumentException("Le RIB est obligatoire.");
        }
        if (compte.getId() == null || !compteRepository.existsById(compte.getId())) {
            compte = compteRepository.save(compte);

            Parieur parieur = new Parieur();
            parieur.setCompte(compte);
            parieur.setRib(rib);
            try {
                return parieurRepository.save(parieur);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la création du parieur : " + e.getMessage(), e);
            }
        }else {
            throw new IllegalArgumentException("Le compte existe déjà.");
        }
    }

    public Parieur updateRib(Long id, String rib) {
        Parieur parieurToUpdate = parieurRepository.findById(id).orElseThrow();
        parieurToUpdate.setRib(rib);
        return parieurRepository.save(parieurToUpdate);
    }

    public Parieur baisserCagnote(Long id, double val){
        Parieur parieurToUpdate = parieurRepository.findById(id).orElseThrow();
        if(parieurToUpdate.getCagnotte() < val){
            throw new IllegalArgumentException("Fonds insuffisants : cagnotte actuelle = " + parieurToUpdate.getCagnotte());
        }
        parieurToUpdate.setCagnotte(parieurToUpdate.getCagnotte() - val);
        return parieurRepository.save(parieurToUpdate);
    }

    public Parieur augmenterCagnote(Long id, double val){
        Parieur parieurToUpdate = parieurRepository.findById(id).orElseThrow();
        parieurToUpdate.setCagnotte(parieurToUpdate.getCagnotte() + val);
        return parieurRepository.save(parieurToUpdate);
    }

}
