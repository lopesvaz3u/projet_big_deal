package dev.shrp.utilisateurs.repositories;

import dev.shrp.utilisateurs.entities.Parieur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParieurRepository extends JpaRepository<Parieur, Long> {
}
