package dev.shrp.match.controllers;

import dev.shrp.match.entities.Competition;
import dev.shrp.match.services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competition")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public List<Competition> getAllCompetitions() {
        return competitionService.getAllCompetitions();
    }

    @GetMapping("/sync")
    public ResponseEntity<String> syncCompetitions() {
        List<Competition> competitions = competitionService.fetchDataCompetition();
        competitionService.saveAllCompetitions(competitions);
        return ResponseEntity.ok("Sauvegarde des compétitions terminée !");
    }

    @GetMapping("/{id}")
    public Optional<Competition> getOneCompetition(@PathVariable("id") Long id) {
        return competitionService.getOneCompetitionById(id);
    }
}
