package dev.shrp.recommendation.controllers;

import dev.shrp.recommendation.dto.RecommendationDTO;
import dev.shrp.recommendation.services.UserBasedRecommender;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private UserBasedRecommender userBasedRecommender;

    @GetMapping("/onUserSim")
    public ArrayList<RecommendationDTO> getRecommandationUserBased(@RequestBody Long id_parieur) throws IOException, TasteException {
        return UserBasedRecommender.getRecommandationUserBased(id_parieur);
    }

}
