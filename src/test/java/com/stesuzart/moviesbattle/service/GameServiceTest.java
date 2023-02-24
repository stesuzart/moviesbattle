package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.commons.GenerateEntityUtils;
import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.entity.User;
import com.stesuzart.moviesbattle.exceptions.GameOverException;
import com.stesuzart.moviesbattle.repository.GameRepository;
import com.stesuzart.moviesbattle.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserService userService;
    @Mock
    private MovieService movieService;
    @Mock
    private RoundService roundService;
    @Mock
    private RankingService rankingService;

    @Test
    public void whenCallsCreateGame_shouldReturnNewGame_withSuccess() {
        User user = GenerateEntityUtils.generateUser();
        Pair<Movie, Movie> movies = GenerateEntityUtils.generateMovies();
        when(userService.getUser()).thenReturn(user);

        when(movieService.getPairMovie())
                .thenReturn(movies);

        when(gameRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        GameResponse gameResponse = gameService.createGame();

        Assertions.assertNotNull(gameResponse);
        Assertions.assertNotNull(gameResponse.id());
        Assertions.assertEquals(2, gameResponse.round().size());
        Assertions.assertEquals(movies.getFirst().getId(), gameResponse.round().get(0).movieId());
        Assertions.assertEquals(movies.getSecond().getId(), gameResponse.round().get(1).movieId());

        Mockito.verify(gameRepository, times(1)).save(argThat(game ->
                Objects.nonNull(game.getId())
                        && Objects.equals(0, game.getScore())
                        && Objects.equals(0, game.getError())
                        && Objects.equals(0, game.getRounds().size())
                        && Objects.equals(user.getId(), game.getPlayerId())
        ));
    }

    @Test
    public void whenCallsNextQuiz_shouldReturnNewRound_withSuccess() {
        User user = GenerateEntityUtils.generateUser();

        Pair<Movie, Movie> oldMovies = GenerateEntityUtils.generateMovies();
        Pair<Movie, Movie> newMovies = GenerateEntityUtils.generateMovies();
        newMovies.getFirst().setId("3");
        newMovies.getSecond().setId("4");

        NextQuizRequest request = new NextQuizRequest(oldMovies.getFirst().getId(), oldMovies.getSecond().getId());
        Game game = GenerateEntityUtils.generateGame(user.getId(), oldMovies);

        when(gameRepository.findById(any()))
                .thenReturn(Optional.of(game));
        when(movieService.getMovie(request.movieWinnerId()))
                .thenReturn(oldMovies.getFirst());
        when(movieService.getMovie(request.movieLoserId()))
                .thenReturn(oldMovies.getSecond());
        when(movieService.getPairMovie())
                .thenReturn(oldMovies).thenReturn(newMovies);

        List<RoundResponse> roundResponses = gameService.nextQuiz(game.getId(), request);

        Assertions.assertNotNull(roundResponses);
        Assertions.assertEquals(2, roundResponses.size());
        Assertions.assertEquals(newMovies.getFirst().getId(), roundResponses.get(0).movieId());
        Assertions.assertEquals(newMovies.getSecond().getId(), roundResponses.get(1).movieId());

        Mockito.verify(gameRepository, times(1)).save(argThat(gameArg ->
                Objects.nonNull(gameArg.getId())
                        && Objects.equals(1, gameArg.getScore())
                        && Objects.equals(0, gameArg.getError())
                        && Objects.equals(1, gameArg.getRounds().size())
                        && Objects.equals(user.getId(), gameArg.getPlayerId())
        ));
    }

    @Test
    public void whenCallsNextQuiz_withMoreThan3Errors_shouldThrowsGameOverException() {
        User user = GenerateEntityUtils.generateUser();

        Pair<Movie, Movie> oldMovies = GenerateEntityUtils.generateMovies();

        NextQuizRequest request = new NextQuizRequest(oldMovies.getFirst().getId(), oldMovies.getSecond().getId());

        Game game = GenerateEntityUtils.generateGame(user.getId(), oldMovies);
        game.setError();
        game.setError();
        game.setError();

        when(gameRepository.findById(any()))
                .thenReturn(Optional.of(game));
        when(movieService.getMovie(request.movieLoserId()))
                .thenReturn(oldMovies.getFirst());
        when(movieService.getMovie(request.movieWinnerId()))
                .thenReturn(oldMovies.getSecond());

        Assertions.assertThrows(GameOverException.class, () -> gameService.nextQuiz(game.getId(), request));
    }
}
