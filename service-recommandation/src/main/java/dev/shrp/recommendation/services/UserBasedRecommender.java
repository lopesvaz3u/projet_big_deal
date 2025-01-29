package dev.shrp.recommendation.services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import dev.shrp.recommendation.dto.MatchDTO;
import dev.shrp.recommendation.dto.MatchPariDTO;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
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
    private List<MatchPariDTO> paris;


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
        paris = new ArrayList<>();

        paris.add(new MatchPariDTO(1L, 1L));
        paris.add(new MatchPariDTO(2L, 1L));
        paris.add(new MatchPariDTO(3L, 1L));

        paris.add(new MatchPariDTO(4L, 2L));
        paris.add(new MatchPariDTO(5L, 2L));
        paris.add(new MatchPariDTO(6L, 2L));

        paris.add(new MatchPariDTO(7L, 3L));
        paris.add(new MatchPariDTO(8L, 3L));
        paris.add(new MatchPariDTO(9L, 3L));

        paris.add(new MatchPariDTO(10L, 4L));
        paris.add(new MatchPariDTO(1L, 4L));
        paris.add(new MatchPariDTO(2L, 4L));

        paris.add(new MatchPariDTO(3L, 5L));
        paris.add(new MatchPariDTO(4L, 5L));
        paris.add(new MatchPariDTO(5L, 5L));

        paris.add(new MatchPariDTO(6L, 6L));
        paris.add(new MatchPariDTO(7L, 6L));
        paris.add(new MatchPariDTO(8L, 6L));

        paris.add(new MatchPariDTO(9L, 7L));
        paris.add(new MatchPariDTO(10L, 7L));
        paris.add(new MatchPariDTO(1L, 7L));

        paris.add(new MatchPariDTO(2L, 8L));
        paris.add(new MatchPariDTO(3L, 8L));
        paris.add(new MatchPariDTO(4L, 8L));

        paris.add(new MatchPariDTO(5L, 9L));
        paris.add(new MatchPariDTO(6L, 9L));
        paris.add(new MatchPariDTO(7L, 9L));

        paris.add(new MatchPariDTO(8L, 10L));
        paris.add(new MatchPariDTO(9L, 10L));
        paris.add(new MatchPariDTO(10L, 10L));

        paris.add(new MatchPariDTO(1L, 11L));
        paris.add(new MatchPariDTO(2L, 11L));
        paris.add(new MatchPariDTO(3L, 11L));

        paris.add(new MatchPariDTO(4L, 12L));
        paris.add(new MatchPariDTO(5L, 12L));
        paris.add(new MatchPariDTO(6L, 12L));

        paris.add(new MatchPariDTO(7L, 13L));
        paris.add(new MatchPariDTO(8L, 13L));
        paris.add(new MatchPariDTO(9L, 13L));

        paris.add(new MatchPariDTO(10L, 14L));
        paris.add(new MatchPariDTO(1L, 14L));
        paris.add(new MatchPariDTO(2L, 14L));

        paris.add(new MatchPariDTO(3L, 15L));
        paris.add(new MatchPariDTO(4L, 15L));
        paris.add(new MatchPariDTO(5L, 15L));

        paris.add(new MatchPariDTO(6L, 16L));
        paris.add(new MatchPariDTO(7L, 16L));
        paris.add(new MatchPariDTO(8L, 16L));

        paris.add(new MatchPariDTO(9L, 17L));
        paris.add(new MatchPariDTO(10L, 17L));
        paris.add(new MatchPariDTO(1L, 17L));

        paris.add(new MatchPariDTO(2L, 18L));
        paris.add(new MatchPariDTO(3L, 18L));
        paris.add(new MatchPariDTO(4L, 18L));

        paris.add(new MatchPariDTO(5L, 19L));
        paris.add(new MatchPariDTO(6L, 19L));
        paris.add(new MatchPariDTO(7L, 19L));

        paris.add(new MatchPariDTO(8L, 20L));
        paris.add(new MatchPariDTO(9L, 20L));
        paris.add(new MatchPariDTO(10L, 20L));
    }

    public void createMatrice(List<MatchDTO> listMatch, List<MatchPariDTO> listMatchPari){
        /*
        try (FileWriter writer = new FileWriter("documents/matrice_user_item.csv")) {
            // Extraire les IDs des matchs
            List<Long> matchIds = new ArrayList<>();
            for (MatchDTO match : listMatch) {
                matchIds.add(match.getId_match());
            }

            // Organiser les paris par parieur
            Map<Long, List<Long>> parieurToMatch = new HashMap<>();
            for (MatchPariDTO pari : listMatchPari) {
                parieurToMatch.computeIfAbsent(pari.getId_parieur(), k -> new ArrayList<>()).add(pari.getId_match());
            }

            // Remplir les lignes pour chaque parieur
            for (Map.Entry<Long, List<Long>> entry : parieurToMatch.entrySet()) {
                Long parieurId = entry.getKey();
                List<Long> matchesParies = entry.getValue();

                // La ligne commence avec l'identifiant du parieur
                StringBuilder line = new StringBuilder();
                line.append(parieurId);
                for (Long matchId : matchIds) {
                    // 1 si le parieur a parié sur le match, 0 sinon
                    line.append(",").append(matchesParies.contains(matchId) ? 1 : 0);
                }
                writer.write(line.toString());
                writer.write("\n");
            }

            System.out.println("Matrice CSV générée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        /*
        try (FileWriter writer = new FileWriter("documents/matrice_user_item.csv")) {
            System.out.println("Début csv");
            // Organiser les paris par parieur
            for (MatchPariDTO pari : listMatchPari) {
                Long parieurId = pari.getId_parieur();
                Long matchId = pari.getId_match();

                // Écriture dans le fichier sous forme "id_parieur, id_match, pari"
                writer.write(parieurId + "," + matchId + ",1\n");
            }

            System.out.println("Matrice CSV générée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        try (FileWriter writer = new FileWriter("documents/matrice_user_item.csv")) {

            // Récupérer l'ensemble des IDs de parieurs
            Set<Long> parieurIds = new HashSet<>();
            for (MatchPariDTO pari : listMatchPari) {
                parieurIds.add(pari.getId_parieur());
            }

            // Écrire l'en-tête du CSV avec tous les matchs
            /*
            writer.write("id_parieur");
            for (MatchDTO match : listMatch) {
                writer.write("," + match.getId_match());
            }
            writer.write("\n");

             */

            // Écrire les lignes pour chaque parieur
            for (Long parieurId : parieurIds) {
                writer.write(parieurId.toString());

                // Pour chaque match, vérifier si le parieur a parié
                for (MatchDTO match : listMatch) {
                    boolean pariPlace = false;
                    for (MatchPariDTO pari : listMatchPari) {
                        if (pari.getId_parieur().equals(parieurId) && pari.getId_match().equals(match.getId_match())) {
                            pariPlace = true;
                            break;
                        }
                    }
                    // Si parié, mettre 1, sinon mettre 0
                    writer.write("," + (pariPlace ? 1 : 0));
                }
                writer.write("\n");
            }

            System.out.println("Matrice CSV générée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<MatchDTO> getMatchs() {
        return matchs;
    }

    public List<MatchPariDTO> getParis() {
        return paris;
    }

    public static void main(String[] args) throws Exception {
        /*
        UserBasedRecommender UBR = new UserBasedRecommender();
        UBR.initDTO();
        UBR.createMatrice(UBR.getMatchs(), UBR.getParis());

        RandomUtils.useTestSeed(); // to randomize the evaluation result
        DataModel model = new FileDataModel(new File("documents/matrice_user_item.csv"));

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };

        // Recommend certain number of items for a particular user
        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recommendations = recommender.recommend(9, 5);
        for (RecommendedItem recommendedItem : recommendations) {
            System.out.println(recommendedItem);
        }

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println("RMSE: " + score);

        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7);

        System.out.println("Precision: " + stats.getPrecision());
        System.out.println("Recall: " + stats.getRecall());
        System.out.println("F1 Score: " + stats.getF1Measure());


         */

        UserBasedRecommender UBR = new UserBasedRecommender();
        UBR.initDTO();
        UBR.createMatrice(UBR.getMatchs(), UBR.getParis());

        RandomUtils.useTestSeed(); // to randomize the evaluation result
        DataModel model = new FileDataModel(new File("documents/matrice_user_item.csv"));

        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {

                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                //SpearmanCorrelationSimilarity similarity = new SpearmanCorrelationSimilarity(model);

                // neighborhood size = 100
                UserNeighborhood neighborhood = new NearestNUserNeighborhood (100, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };

        // Recommend certain number of items for a particular user
        // Here, recommending 5 items to user_id = 9
        Recommender recommender = recommenderBuilder.buildRecommender(model);
        //Le système recommande 5 éléments pour l'utilisateur ayant l'ID 9 en appelant recommend(9, 5).
        List<RecommendedItem> recomendations = recommender.recommend(9, 5);
        for (RecommendedItem recommendedItem : recomendations) {
            System.out.println(recommendedItem);
        }

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
        System.out.println("AUC: " + score);

        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, 4, 0.7); // evaluate precision recall at 10

        System.out.println("Precision: " + stats.getPrecision());
        System.out.println("Recall: " + stats.getRecall());
        System.out.println("F1 Score: " + stats.getF1Measure());


    }
}