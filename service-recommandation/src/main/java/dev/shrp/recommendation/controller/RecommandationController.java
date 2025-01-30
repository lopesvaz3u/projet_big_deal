package dev.shrp.recommendation.controller;

import dev.shrp.recommendation.services.RecommandationService;
import dev.shrp.recommendation.services.RecommendationResult;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/recommandation")
public class RecommandationController {

    @Autowired
    private RecommandationService recommandationService;


    @GetMapping("/{idParieur}")
    public RecommendationResult getRecomm(@PathVariable long idParieur) throws IOException, TasteException {
        return recommandationService.recommend(idParieur);
    }
}
