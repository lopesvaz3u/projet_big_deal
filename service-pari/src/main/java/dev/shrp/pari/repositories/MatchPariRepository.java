package dev.shrp.pari.repositories;

import dev.shrp.pari.entities.MatchPari;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchPariRepository extends JpaRepository<MatchPari, Long> {
    List<MatchPari> findByIdMatch(Long idMatch);
    List<MatchPari> findByIdPari(Long idPari);
}
