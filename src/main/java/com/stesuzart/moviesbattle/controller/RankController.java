package com.stesuzart.moviesbattle.controller;

import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.service.GameService;
import com.stesuzart.moviesbattle.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RankController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking(){
        return ResponseEntity.ok(rankingService.getRanking());
    }
}
