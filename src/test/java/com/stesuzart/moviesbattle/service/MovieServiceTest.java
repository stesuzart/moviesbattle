package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.entity.Ranking;
import com.stesuzart.moviesbattle.repository.MovieRepository;
import com.stesuzart.moviesbattle.repository.RankingRepository;
import com.stesuzart.moviesbattle.service.impl.MovieServiceImpl;
import com.stesuzart.moviesbattle.service.impl.RankingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;
    @Mock
    private MovieRepository movieRepository;

    @Test
    public void whenCallsGetMovie_shouldReturnMovie_withSuccess() {
        String movieId = "filme1";
        Movie movie = new Movie(movieId, "O FIlme 1", 5.5, 100L);

        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

       Movie movieResponse = movieService.getMovie(movieId);

        Assertions.assertNotNull(movieResponse);
        Assertions.assertEquals(movie.getId(), movieResponse.getId());
        Assertions.assertEquals(movie.getTitle(), movieResponse.getTitle());
        Assertions.assertEquals(movie.getRate(), movieResponse.getRate());
        Assertions.assertEquals(movie.getVotes(), movieResponse.getVotes());

        Mockito.verify(movieRepository, times(1)).findById(any());
    }

    @Test
    public void whenCallsGetPairMovie_shouldReturnNewPairMovie_withSuccess() {
        List<Movie> movies = List.of(
                new Movie("filme1", "O FIlme 1", 5.5, 100L),
                new Movie("filme2", "O FIlme 2", 8.5, 100L)
        );
        when(movieRepository.findPairMovie()).thenReturn(movies);

        Pair<Movie, Movie> pairMovieResponse = movieService.getPairMovie();

        Assertions.assertNotNull(pairMovieResponse);
        Assertions.assertEquals(movies.get(0).getId(), pairMovieResponse.getFirst().getId());
        Assertions.assertEquals(movies.get(0).getTitle(), pairMovieResponse.getFirst().getTitle());
        Assertions.assertEquals(movies.get(0).getRate(), pairMovieResponse.getFirst().getRate());
        Assertions.assertEquals(movies.get(0).getVotes(), pairMovieResponse.getFirst().getVotes());
        Assertions.assertEquals(movies.get(1).getId(), pairMovieResponse.getSecond().getId());
        Assertions.assertEquals(movies.get(1).getTitle(), pairMovieResponse.getSecond().getTitle());
        Assertions.assertEquals(movies.get(1).getRate(), pairMovieResponse.getSecond().getRate());
        Assertions.assertEquals(movies.get(1).getVotes(), pairMovieResponse.getSecond().getVotes());

        Mockito.verify(movieRepository, times(1)).findPairMovie();
    }
}
