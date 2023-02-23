package com.stesuzart.moviesbattle.service;


import com.stesuzart.moviesbattle.entity.Movie;
import org.springframework.data.util.Pair;

public interface MovieService {

    Movie getMovie(String id);

    Pair<Movie, Movie> getPairMovie();
}
