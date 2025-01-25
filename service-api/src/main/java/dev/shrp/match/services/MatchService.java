package dev.shrp.match.services;

import dev.shrp.match.entities.Match;
import dev.shrp.match.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getAllMatchs() {
        return matchRepository.findAll();
    }

    public Optional<Match> getOneMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

}
