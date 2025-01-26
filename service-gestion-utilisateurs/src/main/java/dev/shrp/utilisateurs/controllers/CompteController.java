package dev.shrp.utilisateurs.controllers;

import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @GetMapping
    public List<Compte> getAllComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping("/{id}")
    public Optional<Compte> getOneUser(@PathVariable("id") Long id) {
        return compteService.getOneCompteById(id);
    }

    @PostMapping
    public Compte createCompte(@RequestBody Compte compte) {
        return compteService.createCompte(compte);
    }

    @PutMapping("/{id}")
    public Compte updateCompte(@PathVariable("id") Long id, @RequestBody Compte compte) {
        return compteService.updateCompte(id, compte);
    }
}
