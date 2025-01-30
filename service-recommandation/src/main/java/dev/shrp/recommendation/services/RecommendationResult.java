package dev.shrp.recommendation.services;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public class RecommendationResult {
    private List<RecommendedItem> recommendations;
    private double rmseScore;
    private Double precisionPercentage;

    public RecommendationResult(List<RecommendedItem> recommendations, double rmseScore, Double precisionPercentage) {
        this.recommendations = recommendations;
        this.rmseScore = rmseScore;
        this.precisionPercentage = precisionPercentage;
    }

    public List<RecommendedItem> getRecommendations() {
        return recommendations;
    }

    public double getRmseScore() {
        return rmseScore;
    }

    public Double getPrecisionPercentage() {
        return precisionPercentage;
    }
}