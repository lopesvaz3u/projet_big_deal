package dev.shrp.recommendation.controller;

import dev.shrp.recommendation.services.RecommandationService;
import dev.shrp.recommendation.entities.RecommendationResult;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/recommandation")
public class RecommandationController {

    @Autowired
    private RecommandationService recommandationService;


    @GetMapping("onUserSim/{idParieur}")
    public RecommendationResult getRecomm(
            @PathVariable long idParieur,
            @RequestParam(defaultValue = "PEARSON") RecommandationService.AlgorithmType algorithm,
            @RequestParam(defaultValue = "RMS") RecommandationService.EvaluatorType evaluator
    ) throws IOException, TasteException {
        return recommandationService.recommend(idParieur, algorithm, evaluator);
    }

}
