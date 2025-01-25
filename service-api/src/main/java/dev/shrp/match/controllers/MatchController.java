package dev.shrp.match.controllers;

import dev.shrp.match.entities.Match;
import dev.shrp.match.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchService.createMatch(match);
    }
}
