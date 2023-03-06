package com.stesuzart.moviesbattle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity(name = "Game")
@Table(name = "game")
public class Game {

    @Id
    private String id;
    @Column(name = "score", nullable = false)
    private Integer score;
    @Column(name = "error", nullable = false)
    private Integer error;
    @OneToMany(mappedBy = "game")
    private List<Round> rounds;
    @Column(name = "player_id", nullable = false)
    private Long playerId;

    public Game() {
    }

    public Game(Long playerId) {
        this.id = UUID.randomUUID().toString();
        this.score = 0;
        this.error = 0;
        this.rounds = List.of();
        this.playerId = playerId;
    }

    public Game(String id, Integer score, Integer error, List<Round> rounds, Long playerId) {
        this.id = id;
        this.score = score;
        this.error = error;
        this.rounds = rounds;
        this.playerId = playerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore() {
        this.score += 1;
    }

    public Integer getError() {
        return error;
    }

    public void setError() {
        this.error += 1;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

}

