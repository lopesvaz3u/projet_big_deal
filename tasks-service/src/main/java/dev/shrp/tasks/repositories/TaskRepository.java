package dev.shrp.tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.shrp.tasks.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
