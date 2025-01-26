package dev.shrp.match.controllers;

import dev.shrp.match.entities.Match;
import dev.shrp.match.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Match> getAllMatchs() {
        return matchService.getAllMatchs();
    }

    @GetMapping("/{id}")
    public Optional<Match> getOneUser(@PathVariable("id") Long id) {
        return matchService.getOneMatchById(id);
    }

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchService.createMatch(match);
    }


    @PutMapping("/{id}/cote")
    public Match updateCote(@PathVariable Long id, @RequestBody Float newCote) {
        return matchService.updateCote(id, newCote);
    }

    @PutMapping("/{id}/score")
    public Match updateScore(
            @PathVariable Long id,
            @RequestParam Integer scoreEquipe1,
            @RequestParam Integer scoreEquipe2) {
        return matchService.updateScore(id, scoreEquipe1, scoreEquipe2);
    }

    @PutMapping("/{id}/depart-reel")
    public Match updateDepartReel(@PathVariable Long id, @RequestBody Date departReel) {
        return matchService.updateDepartReel(id, departReel);
    }

    @PutMapping("/{id}/etat")
    public Match updateEtat(@PathVariable Long id, @RequestBody Match.EEtatMatch nouvelEtat) {
        return matchService.updateEtat(id, nouvelEtat);
    }

    @GetMapping("/sync")
    public ResponseEntity<String> syncMatchs() {
        matchService.fetchAndSaveMatches();
        return ResponseEntity.ok("Synchronisation des matchs terminée !");
    }

}
