package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;

import java.util.List;

public interface GameService {

    GameResponse createGame();

    List<RoundResponse> nextQuiz(String id, NextQuizRequest request);
}
