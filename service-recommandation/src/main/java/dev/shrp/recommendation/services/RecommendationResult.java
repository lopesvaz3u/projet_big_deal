package dev.shrp.recommendation.services;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public class RecommendationResult {
    private List<RecommendedItem> recommendations;
    private double score;
    private Double precisionPercentage;

    public RecommendationResult(List<RecommendedItem> recommendations, double Score, Double precisionPercentage) {
        this.recommendations = recommendations;
        this.score = Score;
        this.precisionPercentage = precisionPercentage;
    }

    public List<RecommendedItem> getRecommendations() {
        return recommendations;
    }

    public double getScore() {
        return score;
    }

    public Double getPrecisionPercentage() {
        return precisionPercentage;
    }
}