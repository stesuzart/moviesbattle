package com.stesuzart.moviesbattle.controller;

import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/game/start")
    public ResponseEntity<?> createGame(){
        GameResponse game = gameService.createGame();
        return ResponseEntity.ok(game);
    }

    @PostMapping("/game/{id}/quiz")
    public ResponseEntity<?> nextQuiz(@PathVariable String id, @RequestBody NextQuizRequest request){
        List<RoundResponse> round = gameService.nextQuiz(id, request);
        return ResponseEntity.ok(round);
    }

}
