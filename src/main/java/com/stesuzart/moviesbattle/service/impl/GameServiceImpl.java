package com.stesuzart.moviesbattle.service.impl;

import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.exceptions.GameNotFoundException;
import com.stesuzart.moviesbattle.exceptions.GameOverException;
import com.stesuzart.moviesbattle.repository.GameRepository;
import com.stesuzart.moviesbattle.service.GameService;
import com.stesuzart.moviesbattle.service.MovieService;
import com.stesuzart.moviesbattle.service.RankingService;
import com.stesuzart.moviesbattle.service.RoundService;
import com.stesuzart.moviesbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private UserService userService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RoundService roundService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RankingService rankingService;

    @Override
    public GameResponse createGame() {
        Long userId = userService.getUser().getId();
        Game game = generateGame(userId);

        Pair<Movie, Movie> pairMovies = generatePairMovies(game);

        roundService.generateRound(game, pairMovies.getFirst().getId(), pairMovies.getSecond().getId());

        return new GameResponse(
                game.getId(),
                List.of(
                        new RoundResponse(pairMovies.getFirst().getId(), pairMovies.getFirst().getTitle()),
                        new RoundResponse(pairMovies.getSecond().getId(), pairMovies.getSecond().getTitle())
                )
        );
    }

    @Override
    public List<RoundResponse> nextQuiz(String gameId, NextQuizRequest request) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);;
        Movie movieWinner = movieService.getMovie(request.movieWinnerId());
        Movie movieLoser = movieService.getMovie(request.movieLoserId());

        finishPreviousRound(movieWinner, movieLoser, game);
        Pair<Movie, Movie> pairMovies = generatePairMovies(game);
        roundService.generateRound(game, pairMovies.getFirst().getId(), pairMovies.getSecond().getId());

        return List.of(
                new RoundResponse(pairMovies.getFirst().getId(), pairMovies.getFirst().getTitle()),
                new RoundResponse(pairMovies.getSecond().getId(), pairMovies.getSecond().getTitle())
        );
    }

    private void finishPreviousRound(Movie movieWinner, Movie movieLoser, Game game) {
        roundService.setFinishRound(movieWinner.getId(), movieLoser.getId(), game.getId());

        if (checkWinner(movieWinner, movieLoser)) {
            game.setScore();
        } else {
            game.setError();
            checkMaximumError(game);
        }
        gameRepository.save(game);
    }

    private Pair<Movie, Movie> generatePairMovies(Game game) {
        Pair<Movie, Movie> pairMovies;
        do {
            pairMovies = movieService.getPairMovie();
        } while(checkPairMoviesAlreadyExists(game.getRounds(), pairMovies));

        return pairMovies;
    }

    private boolean checkPairMoviesAlreadyExists(List<Round> rounds, Pair<Movie, Movie> newPairMovies) {
        return rounds.stream().anyMatch(round -> {
            if (newPairMovies.getFirst().getId().equals(round.getId().getMovieIdFirst())
                    || newPairMovies.getFirst().getId().equals(round.getId().getMovieIdSecond())) {
                return newPairMovies.getSecond().getId().equals(round.getId().getMovieIdFirst())
                        || newPairMovies.getSecond().getId().equals(round.getId().getMovieIdSecond());
            }
            return false;
        });
    }
    private boolean checkWinner(Movie targetMovie, Movie enemyMovie) {
        final var targetPoints = targetMovie.getRate() * targetMovie.getVotes();
        final var enemyPoints = enemyMovie.getRate() * enemyMovie.getVotes();
        return targetPoints >= enemyPoints;
    }

    private void checkMaximumError(Game game) {
        if(game.getError() > 3) {
            rankingService.saveRanking(game.getPlayerId(), game.getScore());
            throw new GameOverException();
        }
    }

    private Game generateGame(long userId) {
        Game game = new Game(userId);
        gameRepository.save(game);
        return game;
    }
}
