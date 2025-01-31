package dev.shrp.pari.controllers;

import dev.shrp.pari.entities.Pari;
import dev.shrp.pari.services.PariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pari")
public class PariController {

    @Autowired
    private PariService pariService;

    @GetMapping
    public List<Pari> getAllParis() {
        return pariService.getAllParis();
    }

    @GetMapping("/{id}")
    public Optional<Pari> getOnePariById(@PathVariable Long id) {
        return pariService.getOnePariById(id);
    }

    @GetMapping("/parieur/{idParieur}")
    public List<Pari> getAllPariOfParieur(@PathVariable Long idParieur) {
        return pariService.getAllPariOfParieur(idParieur);
    }

    @PostMapping("/simple")
    public ResponseEntity<Pari> createPariSimple(@RequestParam("matchId") Long matchId,
                                                 @RequestParam("parieurId") Long parieurId,
                                                 @RequestParam("resultat") String resultat,
                                                 @RequestParam("mise") Double mise) {
        Pari pari = pariService.createPariSimple(matchId, parieurId, resultat, mise);
        return ResponseEntity.ok(pari);
    }

}
