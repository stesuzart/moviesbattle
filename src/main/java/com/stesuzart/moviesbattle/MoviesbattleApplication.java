package com.stesuzart.moviesbattle;

import com.stesuzart.moviesbattle.webscraping.IMDBCrawler;
import com.stesuzart.moviesbattle.webscraping.UserInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class MoviesbattleApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(MoviesbattleApplication.class, args);
		UserInitializer.setUsers();
		IMDBCrawler.getMovies();
	}

}
