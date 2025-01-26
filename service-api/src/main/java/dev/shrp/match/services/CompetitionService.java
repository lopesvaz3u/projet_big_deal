package dev.shrp.match.services;

import dev.shrp.match.entities.Competition;
import dev.shrp.match.repositories.CompetitionRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    private static final String API_URL = "http://api.football-data.org/v4/competitions";
    private static final String API_TOKEN = "272b655199974a19bc0cce85b1010560";

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> getOneCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    public Competition createCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    private Competition mapJsonToCompetition(JsonNode competitionNode) {
        Competition competition = new Competition();
        try {
            competition.setId_competition(competitionNode.get("id").asLong());
            competition.setNom_competition(competitionNode.get("name").asText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return competition;
    }

    @Transactional
    public void fetchAndSaveCompetitions() {
        System.out.println("on est dedans");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-Auth-Token", API_TOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body());
                JsonNode competitions = rootNode.get("competitions");

                // Récupérer tous les IDs existants pour éviter des appels multiples à la base
                List<Long> existingCompetitions = competitionRepository.findAllIds();

                for (JsonNode competitionNode : competitions) {
                    Competition competition = mapJsonToCompetition(competitionNode);
                    System.out.println("Traitement de la compétition : " + competition.getNom_competition());

                    // Vérification si la compétition existe déjà en base
                    if (competition.getId_competition() == null || !existingCompetitions.contains(competition.getId_competition())) {
                        // Si la compétition n'existe pas, on l'ajoute
                        competitionRepository.save(competition);
                        System.out.println("Compétition sauvegardée : " + competition.getNom_competition());
                    } else {
                        // Si elle existe, on la met à jour
                        Competition existingCompetition = competitionRepository.findById(competition.getId_competition())
                            .orElseThrow(() -> new RuntimeException("Competition not found"));

                        // Mise à jour du nom de la compétition
                        existingCompetition.setNom_competition(competition.getNom_competition());

                        // Sauvegarde et flush pour forcer la mise à jour immédiatement
                        competitionRepository.saveAndFlush(existingCompetition);
                        System.out.println("Compétition mise à jour : " + existingCompetition.getNom_competition());
                    }
                }
            } else {
                System.err.println("Erreur : Code HTTP " + response.statusCode());
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            System.err.println("Conflit de mise à jour, réessayer l'opération.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    /* 
    @Transactional
    public void fetchAndSaveCompetitions() {
        System.out.println("on est dedans");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-Auth-Token", API_TOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response.body());
                JsonNode competitions = rootNode.get("competitions");

                // Récupérer tous les IDs existants pour éviter des appels multiples à la base
                List<Long> existingCompetitions = competitionRepository.findAllIds();

                // Traiter seulement le premier élément
                if (competitions.isArray() && competitions.size() > 0) {
                    JsonNode firstCompetitionNode = competitions.get(0); // Récupérer le premier élément
                    Competition competition = mapJsonToCompetition(firstCompetitionNode);
                    System.out.println("Traitement de la première compétition : " + competition.getNom_competition());

                    // Vérification si la compétition existe déjà en base
                    if (competition.getId_competition() == null || !existingCompetitions.contains(competition.getId_competition())) {
                        // Si la compétition n'existe pas, on l'ajoute
                        competitionRepository.save(competition);
                        System.out.println("Compétition sauvegardée : " + competition.getNom_competition());
                    } else {
                        // Si elle existe, on la met à jour
                        Competition existingCompetition = competitionRepository.findById(competition.getId_competition())
                            .orElseThrow(() -> new RuntimeException("Competition not found"));

                        // Mise à jour du nom de la compétition
                        existingCompetition.setNom_competition(competition.getNom_competition());

                        // Sauvegarde et flush pour forcer la mise à jour immédiatement
                        competitionRepository.saveAndFlush(existingCompetition);
                        System.out.println("Compétition mise à jour : " + existingCompetition.getNom_competition());
                    }
                } else {
                    System.err.println("Aucune compétition à traiter.");
                }
            } else {
                System.err.println("Erreur : Code HTTP " + response.statusCode());
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            System.err.println("Conflit de mise à jour, réessayer l'opération.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        */
}