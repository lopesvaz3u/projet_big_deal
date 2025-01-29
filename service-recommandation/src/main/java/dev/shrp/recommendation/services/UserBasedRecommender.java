package dev.shrp.recommendation.services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import dev.shrp.recommendation.dto.MatchDTO;
import dev.shrp.recommendation.dto.MatchPariDTO;
import dev.shrp.recommendation.dto.PariDTO;
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


    }

    public void createMatrice(List<MatchDTO> listMatch, List<MatchPariDTO> listMatchPari){

        // Créer une matrice
        // id_parieur, id_match, somme_parié



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