package dev.shrp.match.services;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataModel dm = new FileDataModel(new File("data/dataset-recsys.csv"));

        /*
        UserSimilarity similarity1 = new PearsonCorrelationSimilarity(model);
        UserSimilarity similarity2 = new LogLikelihoodSimilarity(model);
        UserSimilarity similarity3 = new TanimotoCoefficientSimilarity(model);
        UserSimilarity similarity4 = new EuclideanDistanceSimilarity(model);
        UserSimilarity similarity5 = new GenericUserSimilarity(model);
        UserSimilarity similarity6 = new SpearmanCorrelationSimilarity(model);

        ItemSimilarity similarity11 = new PearsonCorrelationSimilarity(model);
        ItemSimilarity similarity22 = new LogLikelihoodSimilarity(model);
        ItemSimilarity similarity33 = new TanimotoCoefficientSimilarity(model);
        ItemSimilarity similarity44 = new EuclideanDistanceSimilarity(model);
        ItemSimilarity similarity55 = new GenericUserSimilarity(model);

        // here n = 100, computes 100 nearest neighbors
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);

        new GenericUserBasedRecommender(model, neighborhood, similarity);

        new GenericItemBasedRecommender(model, similarity);

         */
    }
}
