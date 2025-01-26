package dev.shrp.match.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.entities.Match;
import dev.shrp.match.repositories.EquipeRepository;
import dev.shrp.match.repositories.MatchRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;


@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EquipeRepository equipeRepository; // Injection du repository pour Equipe

    // URL de l'API et token
    private static final String API_URL = "http://api.football-data.org/v4/matches";
    private static final String API_TOKEN = "272b655199974a19bc0cce85b1010560";

    public List<Match> getAllMatchs() {
        return matchRepository.findAll();
    }

    public Optional<Match> getOneMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }


    public Match updateCote(Long id, Float newCote) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match non trouvé avec l'ID : " + id));
        match.setCote(newCote);
        return matchRepository.save(match);
    }

    public Match updateScore(Long id, Integer scoreEquipe1, Integer scoreEquipe2) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match non trouvé avec l'ID : " + id));
        match.setScore_equipe_1(scoreEquipe1);
        match.setScore_equipe_2(scoreEquipe2);
        return matchRepository.save(match);
    }

    public Match updateDepartReel(Long id, Date departReel) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match non trouvé avec l'ID : " + id));
        match.setDepart_reel(departReel);
        return matchRepository.save(match);
    }

    public Match updateEtat(Long id, Match.EEtatMatch nouvelEtat) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match non trouvé avec l'ID : " + id));
        match.setEtat(nouvelEtat);
        return matchRepository.save(match);
    }

    // --- Synchronisation avec l'API ---
    @Transactional
    public void fetchAndSaveMatches() {
        try {
            // Étape 1 : Envoyer une requête à l'API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-Auth-Token", API_TOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Étape 2 : Parser les données JSON
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body());
                JsonNode matches = rootNode.get("matches");

                for (JsonNode matchNode : matches) {
                    // Étape 3 : Mapper les données JSON vers l'entité Match
                    Match match = mapJsonToMatch(matchNode);

                    // Vérifier si le match existe déjà dans la base de données
                    if (!matchRepository.existsById(match.getId_match())) {
                        // Étape 4 : Sauvegarder le match dans la base de données
                        matchRepository.save(match);
                        System.out.println("Match sauvegardé : " + match.getId_match());
                    } else {
                        System.out.println("Match déjà présent : " + match.getId_match());
                    }
                }
            } else {
                System.err.println("Erreur : Code HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mapper les données JSON vers l'entité Match
    private Match mapJsonToMatch(JsonNode matchNode) {
        Match match = new Match();
        try {
            match.setId_match(matchNode.get("id").asLong());
            EquipeService.enregistrerEquipe(matchNode.get("homeTeam"), equipeRepository);
            Long equipe1Id = matchNode.get("homeTeam").get("id").asLong();
            Equipe equipe1 = equipeRepository.findById(equipe1Id)
                    .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable pour l'ID : " + equipe1Id));
            match.setId_equipe_1(equipe1);
            EquipeService.enregistrerEquipe(matchNode.get("awayTeam"), equipeRepository);
            Long equipe2Id = matchNode.get("awayTeam").get("id").asLong();
            Equipe equipe2 = equipeRepository.findById(equipe2Id)
                    .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable pour l'ID : " + equipe2Id));
            match.setId_equipe_2(equipe2);

            // Récupérer les scores
            JsonNode score = matchNode.get("score");
            match.setScore_equipe_1(score.get("fullTime").get("homeTeam").asInt(0));
            match.setScore_equipe_2(score.get("fullTime").get("awayTeam").asInt(0));

            // Convertir les dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date matchDate = dateFormat.parse(matchNode.get("utcDate").asText());
            match.setDepart_prevu(matchDate);

            // État du match
            String status = matchNode.get("status").asText();
            match.setEtat(mapStatusToEEtatMatch(status));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return match;
    }

    // Mapper le statut de l'API vers l'énumération EEtatMatch
    private Match.EEtatMatch mapStatusToEEtatMatch(String status) {
        return switch (status) {
            case "SCHEDULED" -> Match.EEtatMatch.Prévu;
            case "LIVE" -> Match.EEtatMatch.En_cours;
            case "FINISHED" -> Match.EEtatMatch.Terminé;
            case "CANCELED" -> Match.EEtatMatch.Annulé;
            default -> Match.EEtatMatch.Prévu;
        };
    }

}