package com.stesuzart.moviesbattle.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PairMoviesId implements Serializable {
    @Column(name = "first_movie_id")
    String movieIdFirst;
    @Column(name = "second_movie_id")
    String movieIdSecond;
    public PairMoviesId() {
    }

    public PairMoviesId(String movieIdFirst, String movieIdSecond) {
        this.movieIdFirst = movieIdFirst;
        this.movieIdSecond = movieIdSecond;
    }

    public String getMovieIdFirst() {
        return movieIdFirst;
    }

    public void setMovieIdFirst(String movieIdFirst) {
        this.movieIdFirst = movieIdFirst;
    }

    public String getMovieIdSecond() {
        return movieIdSecond;
    }

    public void setMovieIdSecond(String movieIdSecond) {
        this.movieIdSecond = movieIdSecond;
    }
}
