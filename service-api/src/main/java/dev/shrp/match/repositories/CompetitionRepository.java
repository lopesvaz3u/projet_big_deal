package dev.shrp.match.repositories;

import dev.shrp.match.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    // Ajouter cette méthode pour récupérer tous les IDs des compétitions
    @Query("SELECT c.id_competition FROM Competition c")
    List<Long> findAllIds();

}
