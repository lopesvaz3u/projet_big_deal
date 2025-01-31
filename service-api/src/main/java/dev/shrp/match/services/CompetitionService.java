package dev.shrp.match.services;

import dev.shrp.match.entities.Competition;
import dev.shrp.match.repositories.CompetitionRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Value("${api.url}")
    private String API_URL;
    @Value("${api.token}")
    private String API_TOKEN;

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> getOneCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    public Competition createCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    public List<Competition> fetchDataCompetition() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL+"/competitions"))
                .header("X-Auth-Token", API_TOKEN)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode competitionsNode = root.get("competitions");
            ArrayList<Competition> competitions = new ArrayList<>();
            for (JsonNode competitionNode : competitionsNode) {
                Competition competition = mapJsonToCompetition(competitionNode);
                competitions.add(competition);
            }
            return competitions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des compétitions");
        }
    }

    private Competition mapJsonToCompetition(JsonNode competitionNode) {
        Competition competition = new Competition();
        try {
            competition.setId_competition(competitionNode.get("id").asLong());
            competition.setNom_competition(competitionNode.get("name").asText());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la conversion du JSON en compétition");
        }
        return competition;
    }

    public void saveAllCompetitions(List<Competition> competitions) {
        try {
            competitionRepository.saveAll(competitions);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des compétitions");
        }
    }
}