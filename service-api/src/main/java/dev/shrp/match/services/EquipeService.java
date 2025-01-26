package dev.shrp.match.services;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.repositories.EquipeRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    private static final String API_URL = "http://api.football-data.org/v4/teams";
    private static final String API_TOKEN = "272b655199974a19bc0cce85b1010560";

    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    public Optional<Equipe> getOneEquipeById(Long id) {
        return equipeRepository.findById(id);
    }

    public Equipe createEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    // --- Synchronisation avec l'API ---
    @Transactional
    public void fetchAndSaveTeams() {
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
                System.out.println(response.body());
                JsonNode teams = rootNode.get("teams");

                for (JsonNode teamNode : teams) {
                    // Étape 3 : Mapper les données JSON vers l'entité Equipe
                    enregistrerEquipe(teamNode, equipeRepository);
                }
            } else {
                System.err.println("Erreur : Code HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enregistrerEquipe (JsonNode teamNode, EquipeRepository er){
        Equipe equipe = mapJsonToEquipe(teamNode);
    
        // Vérifier si l'équipe existe déjà dans la base de données
        if (!er.existsById(equipe.getId_equipe())) {
            er.save(equipe);
            System.out.println("Équipe sauvegardée : " + equipe.getNom());
        } else {
            System.out.println("Équipe déjà présente : " + equipe.getNom());
        }
    }

    // Mapper les données JSON vers l'entité Equipe
    private static Equipe mapJsonToEquipe(JsonNode teamNode) {
        Equipe equipe = new Equipe();
        try {
            equipe.setId_equipe(teamNode.get("id").asLong()); // ID de l'équipe
            equipe.setNom(teamNode.get("name").asText()); // Nom de l'équipe
            if(teamNode.get("area") != null){
                equipe.setPays(teamNode.get("area").get("name").asText()); // Pays d'origine
            } else {
                equipe.setPays("non renseigné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return equipe;
    }

}
