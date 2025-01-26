package dev.shrp.utilisateurs.controllers;

import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.entities.Parieur;
import dev.shrp.utilisateurs.services.ParieurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parieur")
public class ParieurController {

    @Autowired
    private ParieurService parieurService;

    @GetMapping
    public List<Parieur> getAllParieurs() {
        return parieurService.getAllParieurs();
    }

    @GetMapping("/{id}")
    public Optional<Parieur> getOneParieur(@PathVariable("id") Long id) {
        return parieurService.getOneParieurById(id);
    }

    @PostMapping
    public Parieur createParieur(@RequestBody Compte compte, @RequestParam String rib) {
        return parieurService.createParieur(compte, rib);
    }
}
