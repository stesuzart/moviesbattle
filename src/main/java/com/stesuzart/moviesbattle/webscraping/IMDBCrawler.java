package com.stesuzart.moviesbattle.webscraping;


import com.stesuzart.moviesbattle.entity.Movie;
import com.stesuzart.moviesbattle.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class IMDBCrawler {

	@Autowired
	static MovieRepository movieRepository;

	public IMDBCrawler(MovieRepository movieRepository) {
		IMDBCrawler.movieRepository = movieRepository;
	}

	private static Elements getGenre() throws IOException {
		String url = "https://www.imdb.com/feature/genre/?ref_=nv_ch_gr";
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

		Elements links = doc.getElementsByClass("article");
		Elements links2 = links.get(5).getElementsByClass("table-cell primary");

		return links2.select("a[href]");
	}

	public static void getMovies() throws IOException {
		if (movieRepository.count() < 675) {
			Elements links = getGenre();

			for (Element link : links) {

				String urlGenre = link.attr("href");
				String url = "https://www.imdb.com" + urlGenre;
				Document genrePage = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

				Elements moviesLinks = genrePage.getElementsByClass("lister-item mode-advanced");
				for (Element movieNode : moviesLinks) {
					try {
						String title = movieNode.getElementsByClass("lister-item-header").first().child(1).text();
						String votes = movieNode.getElementsByClass("text-muted").get(3).parentNode().childNode(3).attr("data-value");
						String rate = movieNode.getElementsByClass("inline-block ratings-imdb-rating").attr("data-value");
						String idMovie = movieNode.getElementsByClass("ribbonize").attr("data-tconst");

						Movie movie = new Movie(
								idMovie,
								title,
								Double.parseDouble(rate),
								Long.parseLong(votes)
						);

						movieRepository.save(movie);
					} catch (IndexOutOfBoundsException ignored) {
					}
				}
			}
		}
	}
}
