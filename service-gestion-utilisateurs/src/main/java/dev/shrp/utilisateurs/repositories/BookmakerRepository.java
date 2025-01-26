package dev.shrp.utilisateurs.repositories;

import dev.shrp.utilisateurs.entities.Bookmaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmakerRepository extends JpaRepository<Bookmaker, Long> {
}
