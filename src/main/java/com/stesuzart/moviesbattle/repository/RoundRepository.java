package com.stesuzart.moviesbattle.repository;

import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoundRepository extends JpaRepository<Round, PairMoviesId> {

    Optional<Round> findByIdAndGameIdAndStatus(PairMoviesId id, String gameId, RoundStatus status);
}
