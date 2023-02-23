package com.stesuzart.moviesbattle.repository;

import com.stesuzart.moviesbattle.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query(nativeQuery=true, value="SELECT *  FROM movie ORDER BY random() LIMIT 2")
    List<Movie> findPairMovie();

}
