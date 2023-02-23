package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.commons.GenerateEntityUtils;
import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Ranking;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import com.stesuzart.moviesbattle.repository.RankingRepository;
import com.stesuzart.moviesbattle.repository.RoundRepository;
import com.stesuzart.moviesbattle.service.impl.RankingServiceImpl;
import com.stesuzart.moviesbattle.service.impl.RoundServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RankingServiceTest {

    @InjectMocks
    private RankingServiceImpl rankingService;
    @Mock
    private RankingRepository rankingRepository;

    @Test
    public void whenCallsGetRanking_shouldReturnRanking_withSuccess() {
        List<Ranking> rankings = List.of(
                new Ranking(1L, 100),
                new Ranking(2L, 80),
                new Ranking(3L, 50)
        );
        when(rankingRepository.findAllByOrderByScoreDesc()).thenReturn(rankings);

        List<Ranking> rankingsResponse = rankingService.getRanking();

        Assertions.assertNotNull(rankingsResponse);
        Assertions.assertEquals(3, rankingsResponse.size());

        Mockito.verify(rankingRepository, times(1)).findAllByOrderByScoreDesc();
    }

    @Test
    public void whenCallsSaveRanking_shouldSaveRanking_withSuccess() {
        Ranking ranking = new Ranking(1L, 100);

        when(rankingRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Ranking rankingResponse = rankingService.saveRanking(ranking.getPlayerId(), ranking.getScore());

        Assertions.assertNotNull(rankingResponse);
        Assertions.assertEquals(ranking.getPlayerId(), rankingResponse.getPlayerId());
        Assertions.assertEquals(ranking.getScore(), rankingResponse.getScore());

        Mockito.verify(rankingRepository, times(1)).save(argThat(rankArg ->
                        Objects.equals(ranking.getPlayerId(), rankArg.getPlayerId())
                        && Objects.equals(ranking.getScore(), rankArg.getScore())
        ));
    }


}
