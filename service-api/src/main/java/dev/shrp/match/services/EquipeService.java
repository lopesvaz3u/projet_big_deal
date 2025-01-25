package dev.shrp.match.services;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.repositories.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    public Optional<Equipe> getOneEquipeById(Long id) {
        return equipeRepository.findById(id);
    }

    public Equipe createEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

}
