package com.stesuzart.moviesbattle.service.impl;

import com.stesuzart.moviesbattle.entity.Ranking;
import com.stesuzart.moviesbattle.repository.RankingRepository;
import com.stesuzart.moviesbattle.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    @Override
    public List<Ranking> getRanking() {
        return rankingRepository.findAllByOrderByScoreDesc();
    }

    @Override
    public Ranking saveRanking(Long userId, Integer score) {

        return rankingRepository.save(new Ranking(userId, score));
    }
}
