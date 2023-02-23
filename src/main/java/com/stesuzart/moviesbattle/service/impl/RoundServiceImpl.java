package com.stesuzart.moviesbattle.service.impl;

import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import com.stesuzart.moviesbattle.exceptions.RoundNotFoundException;
import com.stesuzart.moviesbattle.repository.RoundRepository;
import com.stesuzart.moviesbattle.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private RoundRepository roundRepository;

    @Override
    public Round setFinishRound(String firstMovieId, String secondMovieId, String gameId) {
        PairMoviesId id = new PairMoviesId(firstMovieId, secondMovieId);
        Optional<Round> round = roundRepository.findByIdAndGameIdAndStatus(id, gameId, RoundStatus.RUNNING);

        if(round.isEmpty()){
            id = new PairMoviesId(secondMovieId, firstMovieId);
            round = roundRepository.findByIdAndGameIdAndStatus(id, gameId, RoundStatus.RUNNING);
        }
        round.ifPresent(value -> value.setStatus(RoundStatus.FINISHED));

        return round.orElseThrow(RoundNotFoundException::new);
    }

    @Override
    public Round save(Round round) {
        return roundRepository.save(round);
    }
    @Override
    public Round generateRound(Game game, String movieFirstId, String movieSecondId) {
        Round round = new Round(new PairMoviesId(movieFirstId, movieSecondId), game, RoundStatus.RUNNING);
        return roundRepository.save(round);
    }
}
