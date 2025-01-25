package dev.shrp.match.services;

import dev.shrp.match.entities.Competition;
import dev.shrp.match.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> getOneCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    public Competition createCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

}
