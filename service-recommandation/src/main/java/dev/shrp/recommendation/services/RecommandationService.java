package dev.shrp.recommendation.services;

import dev.shrp.recommendation.dto.PariDTO;
import dev.shrp.recommendation.entities.RecommendationResult;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
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

    public enum AlgorithmType {
        PEARSON, EUCLIDEAN
    }

    public enum EvaluatorType {
        RMS, AVERAGE_ABSOLUTE
    }

    public RecommendationResult recommend(long userId, AlgorithmType algorithmType, EvaluatorType evaluatorType) throws IOException, TasteException {
        // Initialisation des données
        UserBasedRecommender UBR = new UserBasedRecommender();
        UBR.initDTO();
        RandomUtils.useTestSeed();
        DataModel model = new FileDataModel(new File("documents/matrice_user_item.csv"));

        // Sélection de l'algorithme
        RecommenderBuilder recommenderBuilder = getRecommenderBuilder(algorithmType);

        // Génération des recommandations
        Recommender recommender = recommenderBuilder.buildRecommender(model);
        List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

        // Évaluation du modèle
        RecommenderEvaluator evaluator = getRecommenderEvaluator(evaluatorType);
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);

        // Calcul de la précision
        Optional<Float> maxMontant = UBR.getPari().stream()
                .map(PariDTO::getMontant)
                .max(Float::compareTo);
        Double precisionPercentage = maxMontant.map(max -> 100 - ((score / max) * 100)).orElse(null);

        return new RecommendationResult(recommendations, score, precisionPercentage);
    }

    private RecommenderBuilder getRecommenderBuilder(AlgorithmType algorithmType) {
        return model -> {
            switch (algorithmType) {
                case EUCLIDEAN:
                    return createUserBasedRecommender(model, new EuclideanDistanceSimilarity(model));
                case PEARSON:
                default:
                    return createUserBasedRecommender(model, new PearsonCorrelationSimilarity(model));
            }
        };
    }

    private Recommender createUserBasedRecommender(DataModel model, UserSimilarity similarity) throws TasteException {
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(5, similarity, model);
        return new GenericUserBasedRecommender(model, neighborhood, similarity);
    }

    private RecommenderEvaluator getRecommenderEvaluator(EvaluatorType evaluatorType) {
        switch (evaluatorType) {
            case AVERAGE_ABSOLUTE:
                return new AverageAbsoluteDifferenceRecommenderEvaluator();
            case RMS:
            default:
                return new RMSRecommenderEvaluator();
        }
    }
}
