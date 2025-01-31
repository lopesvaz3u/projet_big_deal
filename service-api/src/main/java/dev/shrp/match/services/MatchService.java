package dev.shrp.match.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.entities.Match;
import dev.shrp.match.repositories.EquipeRepository;
import dev.shrp.match.repositories.MatchRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.url}")
    private String API_URL;
    @Value("${api.token}")
    private String API_TOKEN;

    public List<Match> getAllMatchs() {
        return matchRepository.findAll();
    }

    public Optional<Match> getOneMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match updateDepartReel(Long id, Date departReel) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match non trouvé avec l'ID : " + id));
        match.setDepart_reel(departReel);
        return matchRepository.save(match);
    }

    public void fetchAndSaveMatches() {
        try {
            // Étape 1 : Envoyer une requête à l'API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL+"/matches"))
                    .header("X-Auth-Token", API_TOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body());
                JsonNode matches = rootNode.get("matches");

                for (JsonNode matchNode : matches) {
                    Match match = mapJsonToMatch(matchNode);

                    if (!matchRepository.existsById(match.getId_match())) {
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
            throw new RuntimeException("Erreur lors de la récupération des matchs");
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
            match.setEquipe_1(equipe1.getId_equipe());
            EquipeService.enregistrerEquipe(matchNode.get("awayTeam"), equipeRepository);
            Long equipe2Id = matchNode.get("awayTeam").get("id").asLong();
            Equipe equipe2 = equipeRepository.findById(equipe2Id)
                    .orElseThrow(() -> new IllegalArgumentException("Équipe introuvable pour l'ID : " + equipe2Id));
            match.setEquipe_2(equipe2.getId_equipe());

            // Récupérer les scores
            JsonNode score = matchNode.get("score");
            //match.setScore_equipe_1(score.get("fullTime").get("homeTeam").asInt(0));
            //match.setScore_equipe_2(score.get("fullTime").get("awayTeam").asInt(0));

            // Convertir les dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date matchDate = dateFormat.parse(matchNode.get("utcDate").asText());
            match.setDepart_prevu(matchDate);

            // État du match
            String status = matchNode.get("status").asText();
            match.setEtat(mapStatusToEEtatMatch(status));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la conversion du JSON en match");
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