package com.stesuzart.moviesbattle.service.impl;

import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.exceptions.MovieNotFoundException;
import com.stesuzart.moviesbattle.repository.MovieRepository;
import com.stesuzart.moviesbattle.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie getMovie(String id) {
        return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    public Pair<Movie, Movie> getPairMovie() {
        List<Movie> movies = movieRepository.findPairMovie();
        return Pair.of(movies.get(0), movies.get(1));
    }
}
