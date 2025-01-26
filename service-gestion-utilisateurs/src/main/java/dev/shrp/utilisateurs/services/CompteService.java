package dev.shrp.utilisateurs.services;

import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getOneCompteById(Long id) {
        return compteRepository.findById(id);
    }

    public Compte createCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    public Compte updateCompte(Long id, Compte compte) {
        Compte compteToUpdate = compteRepository.findById(id).orElseThrow();
        compteToUpdate.setNom(compte.getNom());
        compteToUpdate.setPrenom(compte.getPrenom());
        compteToUpdate.setEmail(compte.getEmail());
        compteToUpdate.setAdresse(compte.getAdresse());
        compteToUpdate.setTelephone(compte.getTelephone());
        return compteRepository.save(compteToUpdate);
    }
}
