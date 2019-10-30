package com.example.media.helper;

import com.example.media.entity.GoogleBooks;
import com.example.media.entity.ItunesMusic;
import com.example.media.entity.Media;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DefaultMediaServiceHelper {
	private static Logger log = LoggerFactory.getLogger(DefaultMediaServiceHelper.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${maxResults}")
	private int maxResults;
	@Value("${googleBooksUrl}")
	private String googleBooksUrl;
	@Value("${itunesMusicUrl}")
	private String itunesMusicUrl;

	@Async
	@Timed("get-google-media")
	public CompletableFuture<GoogleBooks> getGoogleBooks(String term) {
		log.info("{} started execution of book fetching.", Thread.currentThread().getName());
		GoogleBooks googleBooks = new GoogleBooks();
		googleBooks.setItems(Collections.emptyList());
		CompletableFuture<GoogleBooks> future = CompletableFuture.supplyAsync(
				() -> restTemplate
						.exchange(googleBooksUrl, HttpMethod.GET, getHeaders(),
								GoogleBooks.class, getRequiredParams(term)).getBody())
				.exceptionally((e) -> {
					log.error(e.getMessage());
					return googleBooks;
				})
				.completeOnTimeout(googleBooks, 60, TimeUnit.SECONDS);

		return future;
	}

	@Async
	@Timed("get-itunes-media")
	public CompletableFuture<ItunesMusic> getItunesMusic(String term) {
		log.info("{} started execution of album fetching.", Thread.currentThread().getName());
		ItunesMusic itunesMusic = new ItunesMusic();
		itunesMusic.setItems(Collections.emptyList());

		CompletableFuture<ItunesMusic> future = CompletableFuture.supplyAsync(
				() -> restTemplate
						.exchange(itunesMusicUrl, HttpMethod.GET, getHeaders(),
								ItunesMusic.class, getRequiredParams(term)).getBody())
				.exceptionally((e) -> {
					log.error(e.getMessage());
					return itunesMusic;
				})
				.completeOnTimeout(itunesMusic, 60, TimeUnit.SECONDS);

		return future;
	}

	private Map<String, String> getRequiredParams(String term) {
		Map<String, String> params = new HashMap<>();
		params.put("maxResults", String.valueOf(maxResults));
		params.put("term", term);
		return params;
	}

	private HttpEntity<String> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		return entity;
	}

	public List<Media> convertToMedia(GoogleBooks books, ItunesMusic albums) {
		log.info("Starting converting all media into common media object.");
		List<Media> mediaList = new ArrayList<>();

		for (int i = 0; i < maxResults; i++) {

			if(mediaList.size() >= maxResults){
				break;
			}

			if (i < books.getItems().size()) {
				GoogleBooks.Item item = books.getItems().get(i);
				addBooksToMedia(books, mediaList, item);
			}

			if (i < albums.getItems().size() && mediaList.size() < maxResults) {
				ItunesMusic.Item item = albums.getItems().get(i);
				addAlbumsToMedia(albums, mediaList, item);
			}
		}
		log.info("Converting finished. Starting sorting.");
		Collections.sort(mediaList);
		log.info("Sorting finished");
		return mediaList;
	}

	private static void addBooksToMedia(GoogleBooks books, List<Media> mediaList, GoogleBooks.Item item) {
		Media media = new Media();
		media.setType(books.getType());
		media.setTitle(item.getTitle());
		media.setAuthors(item.getAuthor().stream().collect(Collectors.joining(", ")));
		mediaList.add(media);
		log.info("{} with title {} added", books.getType(), item.getTitle());
	}

	private static void addAlbumsToMedia(ItunesMusic album, List<Media> mediaList, ItunesMusic.Item item) {
		Media media = new Media();
		media.setType(album.getType());
		media.setTitle(item.getTitle());
		media.setAuthors(item.getAuthor());
		mediaList.add(media);
		log.info("{} with title {} added", album.getType(), item.getTitle());
	}
}
