package dev.shrp.recommendation.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommend {

    /**
     * @param args
     */
    /*
    public static void main(String[] args) {
        try {
            DataModel dm = new FileDataModel(new File("dataset-recsys.csv"));

            ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
            //TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
            //ItemSimilarity sim = new TanimotoCoefficientSimilarity(dm);
            //ItemSimilarity sim = new PearsonCorrelationSimilarity(dm);

            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

            int x = 1;
            for (LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
                long itemId = items.nextLong();
                List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 5);

                for (RecommendedItem recommendation : recommendations) {
                    System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
                }

                x++;
                if (x > 10) System.exit(1);	// generate recommendation for first 10 items only
            }

        } catch (IOException e) {
            System.out.println("There was an error.");
            e.printStackTrace();
        } catch (TasteException e) {
            System.out.println("There was a Taste Exception.");
            e.printStackTrace();
        }

    }

     */

}