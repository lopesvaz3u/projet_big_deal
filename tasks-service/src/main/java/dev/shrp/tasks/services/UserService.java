package dev.shrp.tasks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.shrp.tasks.entities.User;
import dev.shrp.tasks.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllTasks() {
        return userRepository.findAll();
    }

    public Optional<User> getOneUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createTask(User user) {
        return userRepository.save(user);
    }

    public void seed() {
        var user = new User();
        user.id = "f928c455-d2f3-4e30-bf58-178ae041e8c2";
        user.firstname = "John";
        user.lastname = "Doe";
        user.email = "john@doe.com";
        userRepository.save(user);
    }
}
