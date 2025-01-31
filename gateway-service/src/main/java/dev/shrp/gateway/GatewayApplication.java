package dev.shrp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //doit être saisie avant la route /tasks
    // tag::route-locator[]
    @Bean
    public RouteLocator oneTask(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();
        return builder.routes()
                .route(p -> p
                .path("/tasks/{id}")
                .uri(httpUri))
                .build();
    }
    // end::route-locator[]

    // tag::route-locator[]
    @Bean
    public RouteLocator tasks(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();
        return builder.routes()
                .route(p -> p
                .path("/tasks")
                .uri(httpUri))
                .build();
    }
    // end::route-locator[]


    // Route pour la mise à jour du score des matchs
    @Bean
    public RouteLocator updateScoreMatchRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService(); // L'URL du service de match
        return builder.routes()
                .route(p -> p
                        .path("/match/{id}/score") // La route d'API pour mettre à jour le score
                        .uri(matchServiceUri)) // Redirige la requête vers le service des matchs
                .build();
    }

    // Route pour obtenir tous les matchs
    @Bean
    public RouteLocator getAllMatchesRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService(); // L'URL du service de match
        return builder.routes()
                .route(p -> p
                        .path("/match")
                        .uri(matchServiceUri)) // Redirige la requête vers le service des matchs
                .build();
    }

    // Route pour obtenir un match spécifique par ID
    @Bean
    public RouteLocator getMatchByIdRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService();
        return builder.routes()
                .route(p -> p
                        .path("/match/{id}")
                        .uri(matchServiceUri))
                .build();
    }

    // Route pour créer un nouveau match
    @Bean
    public RouteLocator createMatchRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService();
        return builder.routes()
                .route(p -> p
                        .path("/match")
                        .uri(matchServiceUri))
                .build();
    }

    // Route pour mettre à jour la cote d'un match
    @Bean
    public RouteLocator updateCoteRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService();
        return builder.routes()
                .route(p -> p
                        .path("/match/{id}/cote")
                        .uri(matchServiceUri))
                .build();
    }

    // Route pour mettre à jour la date de départ réelle d'un match
    @Bean
    public RouteLocator updateDepartReelRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService();
        return builder.routes()
                .route(p -> p
                        .path("/match/{id}/depart-reel")
                        .uri(matchServiceUri))
                .build();
    }

    // Route pour mettre à jour l'état d'un match
    @Bean
    public RouteLocator updateEtatRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String matchServiceUri = uriConfiguration.getMatchService();
        return builder.routes()
                .route(p -> p
                        .path("/match/{id}/etat")
                        .uri(matchServiceUri))
                .build();
    }

    // Route pour retirer de l'argent de la cagnotte d'un parieur
    @Bean
    public RouteLocator retraitCagnotte(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String utilisateurServiceURI = uriConfiguration.getUtilisateurService();
        return builder.routes()
                .route(p -> p
                        .path("/parieur/retraitCagnote/{id}")
                        .uri(utilisateurServiceURI))
                .build();
    }

    // tag::fallback[]
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    // end::fallback[]

}

// tag::uri-configuration[]
@ConfigurationProperties
class UriConfiguration {

    private String httpbin = "http://tasks-service:8080";

    public String getHttpbin() {
        return httpbin;
    }

    public void setHttpbin(String httpbin) {
        this.httpbin = httpbin;
    }

    private String matchService = "http://match-service:7081"; // URL du service des matchs

    public String getMatchService() {
        return matchService;
    }

    public void setMatchService(String matchService) {
        this.matchService = matchService;
    }


    private String utilisateurService = "http://service-gestion-utilisateurs:7080"; // URL du service des utilisateurs

    public String getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(String utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /*
    private String equipeService = "http://equipe-service:7081"; // URL du service des matchs

    public String getEquipeService() {
        return equipeService;
    }

    public void setEquipeServiceService(String equipeService) {
        this.equipeService = equipeService;
    }

     */
}
// end::uri-configuration[]
// end::code[]
