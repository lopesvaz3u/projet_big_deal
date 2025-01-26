package dev.shrp.utilisateurs.repositories;

import dev.shrp.utilisateurs.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}
