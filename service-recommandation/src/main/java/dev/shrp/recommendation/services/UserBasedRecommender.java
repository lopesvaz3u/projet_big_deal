package dev.shrp.recommendation.services;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import dev.shrp.recommendation.dto.MatchDTO;
import dev.shrp.recommendation.dto.MatchPariDTO;
import dev.shrp.recommendation.dto.PariDTO;
import dev.shrp.recommendation.dto.RecommendationDTO;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class UserBasedRecommender {

    private List<MatchDTO> matchs;
    private List<MatchPariDTO> parisMatch;
    private List<PariDTO> paris;


    public void initDTO(){
        // Création des équipes
        List<Long> equipes = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        // Création des compétitions
        List<Long> competitions = Arrays.asList(1L, 2L, 3L);

        // Création des matchs (états : "Prévu" ou "Terminé")
        matchs = new ArrayList<>();
        matchs.add(new MatchDTO(1L, 1.5f, "Prévu", 1L, 2L, 1L));
        matchs.add(new MatchDTO(2L, 2.3f, "Prévu", 3L, 4L, 1L));
        matchs.add(new MatchDTO(3L, 1.8f, "Prévu", 5L, 6L, 2L));
        matchs.add(new MatchDTO(4L, 1.7f, "Prévu", 7L, 8L, 2L));
        matchs.add(new MatchDTO(5L, 2.1f, "Prévu", 9L, 10L, 3L));

        matchs.add(new MatchDTO(6L, 1.6f, "Terminé", 1L, 3L, 1L));
        matchs.add(new MatchDTO(7L, 2.4f, "Terminé", 2L, 4L, 1L));
        matchs.add(new MatchDTO(8L, 1.9f, "Terminé", 5L, 7L, 2L));
        matchs.add(new MatchDTO(9L, 1.4f, "Terminé", 6L, 8L, 2L));
        matchs.add(new MatchDTO(10L, 2.0f, "Terminé", 9L, 10L, 3L));

        // Création des parieurs
        List<Long> parieurs = new ArrayList<>();
        for (long i = 1; i <= 20; i++) {
            parieurs.add(i);
        }

        // Création des paris (au moins 3 paris par parieur, aucun doublon sur un match)
        parisMatch = new ArrayList<>();
        paris = new ArrayList<>();


        // Parieur 1L
        paris.add(new PariDTO(1L, 1L, 6));
        parisMatch.add(new MatchPariDTO(1L, 1L));
        paris.add(new PariDTO(2L, 1L, 9));
        parisMatch.add(new MatchPariDTO(2L, 2L));
        paris.add(new PariDTO(3L, 1L, 12));
        parisMatch.add(new MatchPariDTO(3L, 3L));
        paris.add(new PariDTO(4L, 1L, 18)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(4L, 4L)); // Paris sur un autre match
        paris.add(new PariDTO(5L, 1L, 25)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(5L, 5L)); // Paris sur un autre match
        paris.add(new PariDTO(6L, 1L, 30)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(6L, 6L)); // Paris sur un autre match

        // Parieur 2L
        paris.add(new PariDTO(7L, 2L, 10));
        parisMatch.add(new MatchPariDTO(2L, 7L));
        paris.add(new PariDTO(8L, 2L, 15));
        parisMatch.add(new MatchPariDTO(4L, 8L));
        paris.add(new PariDTO(9L, 2L, 21));
        parisMatch.add(new MatchPariDTO(6L, 9L));
        paris.add(new PariDTO(10L, 2L, 30)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(8L, 10L)); // Paris sur un autre match
        paris.add(new PariDTO(11L, 2L, 35)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(10L, 11L)); // Paris sur un autre match
        paris.add(new PariDTO(12L, 2L, 40)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(13L, 12L)); // Paris sur un autre match

        // Parieur 3L
        paris.add(new PariDTO(13L, 3L, 3));
        parisMatch.add(new MatchPariDTO(10L, 13L));
        paris.add(new PariDTO(14L, 3L, 40));
        parisMatch.add(new MatchPariDTO(9L, 14L));
        paris.add(new PariDTO(15L, 3L, 9));
        parisMatch.add(new MatchPariDTO(8L, 15L));
        paris.add(new PariDTO(16L, 3L, 18)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(7L, 16L)); // Paris sur un autre match
        paris.add(new PariDTO(17L, 3L, 22)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(6L, 17L)); // Paris sur un autre match
        paris.add(new PariDTO(18L, 3L, 28)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(5L, 18L)); // Paris sur un autre match

        // Parieur 4L
        paris.add(new PariDTO(19L, 4L, 14));
        parisMatch.add(new MatchPariDTO(9L, 19L));
        paris.add(new PariDTO(20L, 4L, 18));
        parisMatch.add(new MatchPariDTO(2L, 20L));
        paris.add(new PariDTO(21L, 4L, 22));
        parisMatch.add(new MatchPariDTO(1L, 21L));
        paris.add(new PariDTO(22L, 4L, 30)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(5L, 22L)); // Paris sur un autre match
        paris.add(new PariDTO(23L, 4L, 33)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(3L, 23L)); // Paris sur un autre match
        paris.add(new PariDTO(24L, 4L, 38)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(7L, 24L)); // Paris sur un autre match

        // Parieur 5L
        paris.add(new PariDTO(25L, 5L, 8));
        parisMatch.add(new MatchPariDTO(5L, 25L));
        paris.add(new PariDTO(26L, 5L, 19));
        parisMatch.add(new MatchPariDTO(6L, 26L));
        paris.add(new PariDTO(27L, 5L, 25));
        parisMatch.add(new MatchPariDTO(2L, 27L));
        paris.add(new PariDTO(28L, 5L, 12)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(8L, 28L)); // Paris sur un autre match
        paris.add(new PariDTO(29L, 5L, 16)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(9L, 29L)); // Paris sur un autre match
        paris.add(new PariDTO(30L, 5L, 20)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(3L, 30L)); // Paris sur un autre match

        // Parieur 6L
        paris.add(new PariDTO(31L, 6L, 7));
        parisMatch.add(new MatchPariDTO(1L, 31L));
        paris.add(new PariDTO(32L, 6L, 20));
        parisMatch.add(new MatchPariDTO(3L, 32L));
        paris.add(new PariDTO(33L, 6L, 30));
        parisMatch.add(new MatchPariDTO(4L, 33L));
        paris.add(new PariDTO(34L, 6L, 10)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(8L, 34L)); // Paris sur un autre match
        paris.add(new PariDTO(35L, 6L, 18)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(9L, 35L)); // Paris sur un autre match
        paris.add(new PariDTO(36L, 6L, 22)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(10L, 36L)); // Paris sur un autre match


        // Parieur 7L
        paris.add(new PariDTO(37L, 7L, 12));
        parisMatch.add(new MatchPariDTO(3L, 37L));
        paris.add(new PariDTO(38L, 7L, 16));
        parisMatch.add(new MatchPariDTO(8L, 38L));
        paris.add(new PariDTO(39L, 7L, 24));
        parisMatch.add(new MatchPariDTO(9L, 39L));
        paris.add(new PariDTO(40L, 7L, 22)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(4L, 40L)); // Paris sur un autre match
        paris.add(new PariDTO(41L, 7L, 19)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(1L, 41L)); // Paris sur un autre match
        paris.add(new PariDTO(42L, 7L, 26)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(2L, 42L)); // Paris sur un autre match

        // Parieur 8L
        paris.add(new PariDTO(43L, 8L, 5));
        parisMatch.add(new MatchPariDTO(3L, 43L));
        paris.add(new PariDTO(44L, 8L, 13));
        parisMatch.add(new MatchPariDTO(4L, 44L));
        paris.add(new PariDTO(45L, 8L, 27));
        parisMatch.add(new MatchPariDTO(5L, 45L));
        paris.add(new PariDTO(46L, 8L, 12)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(9L, 46L)); // Paris sur un autre match
        paris.add(new PariDTO(47L, 8L, 16)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(10L, 47L)); // Paris sur un autre match
        paris.add(new PariDTO(48L, 8L, 23)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(1L, 48L)); // Paris sur un autre match

        // Parieur 9L
        paris.add(new PariDTO(49L, 9L, 11));
        parisMatch.add(new MatchPariDTO(9L, 49L));
        paris.add(new PariDTO(50L, 9L, 17));
        parisMatch.add(new MatchPariDTO(5L, 50L));
        paris.add(new PariDTO(51L, 9L, 23));
        parisMatch.add(new MatchPariDTO(1L, 51L));
        paris.add(new PariDTO(52L, 9L, 18)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(2L, 52L)); // Paris sur un autre match
        paris.add(new PariDTO(53L, 9L, 25)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(4L, 53L)); // Paris sur un autre match
        paris.add(new PariDTO(54L, 9L, 30)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(10L, 54L)); // Paris sur un autre match

        // Parieur 10L
        paris.add(new PariDTO(55L, 10L, 6));
        parisMatch.add(new MatchPariDTO(5L, 55L));
        paris.add(new PariDTO(56L, 10L, 15));
        parisMatch.add(new MatchPariDTO(6L, 56L));
        paris.add(new PariDTO(57L, 10L, 21));
        parisMatch.add(new MatchPariDTO(7L, 57L));
        paris.add(new PariDTO(58L, 10L, 13)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(1L, 58L)); // Paris sur un autre match
        paris.add(new PariDTO(59L, 10L, 19)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(3L, 59L)); // Paris sur un autre match
        paris.add(new PariDTO(60L, 10L, 25)); // Paris supplémentaire
        parisMatch.add(new MatchPariDTO(10L, 60L)); // Paris sur un autre match


    }

    public void createMatrice(List<MatchDTO> listMatch, List<MatchPariDTO> listMatchPari, List<PariDTO> listPari){

        String fileName = "documents/matrice_user_item.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Créer une matrice
            // id_parieur, id_match, somme_parié

            // Création d'une map pour stocker les sommes pariées par match et parieur
            Map<Long, Map<Long, Float>> matrice = new HashMap<>();

            for (PariDTO pari : listPari) {
                long parieurId = pari.getId_parieur();
                float montant = pari.getMontant();

                for (MatchPariDTO matchPari : listMatchPari) {
                    if (matchPari.getId_pari() == pari.getId_pari()) {
                        long matchId = matchPari.getId_match();

                        matrice.putIfAbsent(parieurId, new HashMap<>());
                        matrice.get(parieurId).merge(matchId, montant, Float::sum);
                    }
                }
            }

            // Écriture des données dans le fichier CSV
            for (Map.Entry<Long, Map<Long, Float>> entry : matrice.entrySet()) {
                long parieurId = entry.getKey();
                for (Map.Entry<Long, Float> matchEntry : entry.getValue().entrySet()) {
                    writer.write(parieurId + "," + matchEntry.getKey() + "," + matchEntry.getValue() + "\n");
                }
            }

            System.out.println("Fichier CSV généré avec succès: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<MatchDTO> getMatchs() {
        return matchs;
    }

    public List<MatchPariDTO> getMatchParis() {
        return parisMatch;
    }

    public List<PariDTO> getPari() {
        return paris;
    }

    public static ArrayList<RecommendationDTO> getRecommandationUserBased(Long id_parieur) throws IOException, TasteException {
        UserBasedRecommender UBR = new UserBasedRecommender();
        UBR.initDTO();
        UBR.createMatrice(UBR.getMatchs(), UBR.getMatchParis(), UBR.getPari());

        RandomUtils.useTestSeed(); // to randomize the evaluation result
        DataModel model = new FileDataModel(new File("documents/matrice_user_item.csv"));

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {

                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                //SpearmanCorrelationSimilarity similarity = new SpearmanCorrelationSimilarity(model);

                // neighborhood size = 20
                UserNeighborhood neighborhood = new NearestNUserNeighborhood (5, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };

        // Recommend certain number of items for a particular user

        long userId = id_parieur; // l'ID du parieur que tu veux cibler
        int numRecommendations = 3; // Nombre de recommandations souhaité

        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recomendations = recommender.recommend(userId, numRecommendations);

        Set<Long> matchsPrevus = UBR.getMatchs().stream()
                .filter(match -> "Prévu".equals(match.getEtat()))
                .map(MatchDTO::getId_match)
                .collect(Collectors.toSet());



        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println("RMSE: " + score);

        // Exemple pour obtenir le montant max
        Optional<Float> maxMontant = UBR.getPari().stream()
                .map(PariDTO::getMontant) // On récupère les montants
                .max(Float::compareTo);  // On cherche le maximum

        Double precision = maxMontant
                .map(max -> (100 - ((score / max) * 100)))
                .orElse(Double.valueOf(1));

        maxMontant.ifPresent(max -> System.out.println("Soit " + precision + "% de précision de la recommendation"));

        ArrayList<RecommendationDTO> resultat = new ArrayList<RecommendationDTO>();

        for (RecommendedItem recommendedItem : recomendations) {
            //if(matchsPrevus.contains(recommendedItem.getItemID())){
            System.out.println(recommendedItem);
            resultat.add(new RecommendationDTO(userId, recommendedItem.getItemID(), precision));
            //}
        }

        return resultat;
    }

    /*
    public static void main(String[] args) throws Exception {


    }

     */
}