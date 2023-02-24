package com.stesuzart.moviesbattle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stesuzart.moviesbattle.controller.request.NextQuizRequest;
import com.stesuzart.moviesbattle.controller.response.GameResponse;
import com.stesuzart.moviesbattle.controller.response.RoundResponse;
import com.stesuzart.moviesbattle.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private RankController rankController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenAGameStartEndpoint_whenCallsCreateGame_shouldReturnGameId() throws Exception {
        GameResponse game = new GameResponse("1", List.of(new RoundResponse("round1", "O Round 1")));

        Mockito.when(gameService.createGame())
                .thenReturn(game);

        var request = post("/game/start")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(request);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(game.id())));

        verify(gameService, times(1)).createGame();
    }

    @Test
    public void givenAGameQuiz_whenCallsNextQuiz_shouldReturnNewRound() throws Exception {
        String gameId = "game1";
        NextQuizRequest nextQuizRequest = new NextQuizRequest("movie1", "movie2");
        List<RoundResponse> rounds = List.of(
                new RoundResponse("movie3", "O Movie 3"),
                new RoundResponse("movie4", "O Movie 4")
        );

        Mockito.when(gameService.nextQuiz(gameId, nextQuizRequest))
                .thenReturn(rounds);

        var request = post("/game/{gameId}/quiz", gameId)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(nextQuizRequest));

        var response = this.mockMvc.perform(request);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].movieId", equalTo(rounds.get(0).movieId())))
                .andExpect(jsonPath("$.[1].movieId", equalTo(rounds.get(1).movieId())));

        verify(gameService, times(1)).nextQuiz(any(), any());
    }
}
