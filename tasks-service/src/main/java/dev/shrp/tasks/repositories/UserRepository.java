package dev.shrp.tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.shrp.tasks.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
