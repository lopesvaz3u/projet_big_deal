package dev.shrp.tasks.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.shrp.tasks.entities.User;
import dev.shrp.tasks.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Optional<User> getOneUser(@PathVariable("id") Long id) {
        return userService.getOneUserById(id);
    }

    @PostMapping
    public User createTask(@RequestBody User user) {
        return userService.createTask(user);
    }
}
