package dev.shrp.match.controllers;

import dev.shrp.match.entities.Competition;
import dev.shrp.match.services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public Optional<Competition> getOneUser(@PathVariable("id") Long id) {
        return competitionService.getOneCompetitionById(id);
    }

    @PostMapping
    public Competition createCompetition(@RequestBody Competition competition) {
        return competitionService.createCompetition(competition);
    }
}
