package dev.shrp.utilisateurs.controllers;

import dev.shrp.utilisateurs.entities.Bookmaker;
import dev.shrp.utilisateurs.entities.Compte;
import dev.shrp.utilisateurs.services.BookmakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookmaker")
public class BookmakerController {

    @Autowired
    private BookmakerService bookmakerService;

    @GetMapping
    public List<Bookmaker> getAllBookmakers() {
        return bookmakerService.getAllBookmakers();
    }

    @GetMapping("/{id}")
    public Optional<Bookmaker> getOneBookmaker(@PathVariable("id") Long id) {
        return bookmakerService.getOneBookmaerById(id);
    }

    @PostMapping
    public Bookmaker createBookmaker(@RequestBody Compte compte) {
        return bookmakerService.createBookmaker(compte);
    }
}
