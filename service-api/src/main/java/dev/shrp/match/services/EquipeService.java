package dev.shrp.match.services;

import dev.shrp.match.entities.Equipe;
import dev.shrp.match.repositories.EquipeRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.url}")
    private String API_URL;
    @Value("${api.token}")
    private String API_TOKEN;

    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    public Optional<Equipe> getOneEquipeById(Long id) {
        return equipeRepository.findById(id);
    }

    public Equipe createEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public void fetchAndSaveTeams() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL+"/teams"))
                    .header("X-Auth-Token", API_TOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                
                JsonNode rootNode = mapper.readTree(response.body());
                System.out.println(response.body());
                JsonNode teams = rootNode.get("teams");

                for (JsonNode teamNode : teams) {
                    equipeRepository.save(mapJsonToEquipe(teamNode));
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
    public static Equipe mapJsonToEquipe(JsonNode teamNode) {
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
