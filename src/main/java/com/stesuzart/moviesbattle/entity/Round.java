package com.stesuzart.moviesbattle.entity;

import org.springframework.data.util.Pair;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Round")
@Table(name = "round")
public class Round {
    @EmbeddedId
    private PairMoviesId id;
    @Column
    @Enumerated(EnumType.STRING)
    private RoundStatus status;
    @ManyToOne
    private Game game;
    public Round() {

    }
    public Round(PairMoviesId id, Game game, RoundStatus status) {
        this.id = id;
        this.game = game;
        this.status = status;
    }

    public Round(PairMoviesId id, RoundStatus status) {
        this.id = id;
        this.status = status;
    }

    public PairMoviesId getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setId(PairMoviesId id) {
        this.id = id;
    }

    public RoundStatus getStatus() {
        return status;
    }

    public void setStatus(RoundStatus status) {
        this.status = status;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
