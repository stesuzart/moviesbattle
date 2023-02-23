package com.stesuzart.moviesbattle.repository;

import com.stesuzart.moviesbattle.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    List<Ranking> findAllByOrderByScoreDesc();
}
