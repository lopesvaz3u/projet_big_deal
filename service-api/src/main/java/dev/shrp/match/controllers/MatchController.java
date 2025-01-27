package dev.shrp.match.controllers;

import dev.shrp.match.entities.Match;
import dev.shrp.match.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/sync")
    public ResponseEntity<String> syncMatchs() {
        matchService.fetchAndSaveMatches();
        return ResponseEntity.ok("Synchronisation des matchs terminée !");
    }

}
