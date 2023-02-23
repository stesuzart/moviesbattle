package com.stesuzart.moviesbattle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Ranking")
@Table(name = "ranking")
public class Ranking {
    @Id
    private Long playerId;

    @Column(name = "score", nullable = false)
    private Integer score;

    public Ranking() {

    }

    public Ranking(Long playerId, Integer score) {
        this.playerId = playerId;
        this.score = score;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
