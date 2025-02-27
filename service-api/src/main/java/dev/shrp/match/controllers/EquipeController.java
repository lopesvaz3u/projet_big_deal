package dev.shrp.match.controllers;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.services.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @GetMapping
    public List<Equipe> getAllEquipes() {
        return equipeService.getAllEquipes();
    }

    @GetMapping("/{id}")
    public Optional<Equipe> getOneEquip(@PathVariable("id") Long id) {
        return equipeService.getOneEquipeById(id);
    }

    @GetMapping("/sync")
    public ResponseEntity<String> syncEquipes() {
        equipeService.fetchAndSaveTeams();
        return ResponseEntity.ok("Synchronisation des équipes terminée !");
    }
}
