package dev.shrp.match.repositories;

import dev.shrp.match.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
