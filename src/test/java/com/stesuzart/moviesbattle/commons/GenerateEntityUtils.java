package com.stesuzart.moviesbattle.commons;

import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import com.stesuzart.moviesbattle.entity.User;
import org.springframework.data.util.Pair;

import java.util.List;

public class GenerateEntityUtils {
    public static Game generateGame(Long userId, Pair<Movie, Movie> oldMovies) {
        Game game = new Game("1", 0,0, List.of(
                new Round(
                        new PairMoviesId(
                                oldMovies.getFirst().getId(),
                                oldMovies.getSecond().getId()),
                        RoundStatus.RUNNING)),
                userId);
        return game;
    }

    public static User generateUser() {
        return new User(1L, "teste", "Teste", "123");
    }

    public static Pair<Movie, Movie> generateMovies() {
        return Pair.of(
                new Movie("1", "Filme 1", 8.5, 100L),
                new Movie("2", "Filme 2", 7.5, 100L)
        );
    }
}
