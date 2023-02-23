package com.stesuzart.moviesbattle.service;


import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.Round;

public interface RoundService {

    Round setFinishRound(String firstMovieId, String secondMovieId, String gameId);

    Round save(Round round);

    Round generateRound(Game game, String movieFirstId, String movieSecondId);
}
