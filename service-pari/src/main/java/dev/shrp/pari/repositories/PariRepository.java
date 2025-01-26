package dev.shrp.pari.repositories;

import dev.shrp.pari.entities.Pari;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PariRepository extends JpaRepository<Pari, Long> {
    List<Pari> findByIdParieur(Long idParieur);
}
