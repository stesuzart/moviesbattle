package com.stesuzart.moviesbattle.controller;

import com.stesuzart.moviesbattle.entity.Ranking;
import com.stesuzart.moviesbattle.service.RankingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameController gameController;

    @MockBean
    private RankingService rankingService;

    @Test
    public void givenARankingEndpoint_whenCallsGetRanking_shouldReturnListRankings() throws Exception {
        List<Ranking> rankings = List.of(new Ranking(1L, 100), new Ranking(2L, 80));

        Mockito.when(rankingService.getRanking())
                .thenReturn(rankings);

        var request = get("/ranking")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(request);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].playerId", equalTo(1)))
                .andExpect(jsonPath("$.[0].score", equalTo(rankings.get(0).getScore())))
                .andExpect(jsonPath("$.[1].playerId", equalTo(2)))
                .andExpect(jsonPath("$.[1].score", equalTo(rankings.get(1).getScore())));

        verify(rankingService, times(1)).getRanking();
    }
}
