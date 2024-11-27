package dev.shrp.tasks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.shrp.tasks.entities.Task;
import dev.shrp.tasks.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getOneTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void seed() {
        var task = new Task();
        task.content = "faire du sport";
        task.user_id = "f928c455-d2f3-4e30-bf58-178ae041e8c2";
        taskRepository.save(task);
    }
}
