package com.stesuzart.moviesbattle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Movie")
@Table(name = "movie")
public class Movie {
    @Id String id;
    @Column(name = "title", nullable = false) String title;
    @Column(name = "rate", nullable = false) Double rate;
    @Column(name = "votes", nullable = false) Long votes;

    public Movie() {

    }

    public Movie(String id, String title, Double rate, Long votes) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
