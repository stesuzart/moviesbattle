package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.commons.GenerateEntityUtils;
import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import com.stesuzart.moviesbattle.entity.User;
import com.stesuzart.moviesbattle.exceptions.GameOverException;
import com.stesuzart.moviesbattle.repository.GameRepository;
import com.stesuzart.moviesbattle.repository.RoundRepository;
import com.stesuzart.moviesbattle.service.impl.GameServiceImpl;
import com.stesuzart.moviesbattle.service.impl.RoundServiceImpl;
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
public class RoundServiceTest {

    @InjectMocks
    private RoundServiceImpl roundService;
    @Mock
    private RoundRepository roundRepository;

    @Test
    public void whenCallsSetFinishRound_shouldReturnRoundFinished_withSuccess() {
        String movieIdFirst = "movieIdFirst";
        String movieIdSecond = "movieIdSecond";
        PairMoviesId movieId = new PairMoviesId(movieIdFirst, movieIdSecond);
        Game game = GenerateEntityUtils.generateGame(1L, GenerateEntityUtils.generateMovies());
        Optional<Round> round = Optional.of(new Round(movieId, game, RoundStatus.RUNNING));
        when(roundRepository.findByIdAndGameIdAndStatus(
                any(), any(), any()))
                .thenReturn(Optional.empty())
                .thenReturn(round);

        Round roundResponse = roundService.setFinishRound(movieIdFirst, movieIdSecond, game.getId());

        Assertions.assertNotNull(roundResponse);
        Assertions.assertNotNull(roundResponse.getId());
        Assertions.assertEquals(movieIdFirst, roundResponse.getId().getMovieIdFirst());
        Assertions.assertEquals(movieIdSecond, roundResponse.getId().getMovieIdSecond());
        Assertions.assertEquals(RoundStatus.FINISHED, roundResponse.getStatus());

        Mockito.verify(roundRepository, times(2)).findByIdAndGameIdAndStatus(any(), any(), any());
    }

    @Test
    public void whenCallsSave_shouldReturnRoundSaved_withSuccess() {
        String movieIdFirst = "movieIdFirst";
        String movieIdSecond = "movieIdSecond";
        PairMoviesId movieId = new PairMoviesId(movieIdFirst, movieIdSecond);
        Game game = GenerateEntityUtils.generateGame(1L, GenerateEntityUtils.generateMovies());
        Round round = Optional.of(new Round(movieId, game, RoundStatus.RUNNING)).get();

        when(roundRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Round roundResponse = roundService.save(round);

        Assertions.assertNotNull(roundResponse);
        Assertions.assertNotNull(roundResponse.getId());
        Assertions.assertEquals(movieIdFirst, roundResponse.getId().getMovieIdFirst());
        Assertions.assertEquals(movieIdSecond, roundResponse.getId().getMovieIdSecond());
        Assertions.assertEquals(RoundStatus.RUNNING, roundResponse.getStatus());

        Mockito.verify(roundRepository, times(1)).save(argThat(roundArg ->
                Objects.nonNull(roundArg.getId())
                        && Objects.equals(round.getId().getMovieIdFirst(), roundArg.getId().getMovieIdFirst())
                        && Objects.equals(round.getId().getMovieIdSecond(), roundArg.getId().getMovieIdSecond())
                        && Objects.equals(round.getGame(), roundArg.getGame())
                        && Objects.equals(round.getStatus(), roundArg.getStatus())
        ));
    }

    @Test
    public void whenCallsGenerateRound_shouldReturnNewRound_withSuccess() {
        String movieIdFirst = "movieIdFirst";
        String movieIdSecond = "movieIdSecond";
        Game game = GenerateEntityUtils.generateGame(1L, GenerateEntityUtils.generateMovies());

        when(roundRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Round roundResponse = roundService.generateRound(game, movieIdFirst, movieIdSecond);

        Assertions.assertNotNull(roundResponse);
        Assertions.assertNotNull(roundResponse.getId());
        Assertions.assertEquals(movieIdFirst, roundResponse.getId().getMovieIdFirst());
        Assertions.assertEquals(movieIdSecond, roundResponse.getId().getMovieIdSecond());
        Assertions.assertEquals(RoundStatus.RUNNING, roundResponse.getStatus());

        Mockito.verify(roundRepository, times(1)).save(argThat(roundArg ->
                Objects.nonNull(roundArg.getId())
                        && Objects.equals(movieIdFirst, roundArg.getId().getMovieIdFirst())
                        && Objects.equals(movieIdSecond, roundArg.getId().getMovieIdSecond())
                        && Objects.equals(game, roundArg.getGame())
                        && Objects.equals(RoundStatus.RUNNING, roundArg.getStatus())
        ));
    }


}
