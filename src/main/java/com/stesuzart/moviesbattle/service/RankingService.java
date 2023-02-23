package com.stesuzart.moviesbattle.service;


import com.stesuzart.moviesbattle.entity.Ranking;

import java.util.List;

public interface RankingService {

    List<Ranking> getRanking();

    Ranking saveRanking(Long userId, Integer score);
}
