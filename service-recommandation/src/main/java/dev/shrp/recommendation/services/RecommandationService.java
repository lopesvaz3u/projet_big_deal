package dev.shrp.recommendation.services;

import dev.shrp.recommendation.dto.PariDTO;
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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RecommandationService {

    public RecommendationResult recommend(long userId) throws IOException, TasteException {
        UserBasedRecommender UBR = new UserBasedRecommender();
        UBR.initDTO();
        //UBR.createMatrice(UBR.getMatchs(), UBR.getMatchParis(), UBR.getPari());

        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File("documents/matrice_user_item.csv"));

        RecommenderBuilder recommenderBuilder = model1 -> {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model1);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model1);
            return new GenericUserBasedRecommender(model1, neighborhood, similarity);
        };

        int numRecommendations = 3;

        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

        RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);

        Optional<Float> maxMontant = UBR.getPari().stream()
                .map(PariDTO::getMontant)
                .max(Float::compareTo);

        Double precisionPercentage = maxMontant.map(max -> 100 - ((score / max) * 100)).orElse(null);

        return new RecommendationResult(recommendations, score, precisionPercentage);
    }
}
