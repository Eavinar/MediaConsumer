package com.example.media.service;

import com.example.media.entity.Media;
import com.example.media.helper.DefaultMediaServiceHelper;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Primary
@Service
public class DefaultMediaService implements MainService {
	private static final Logger log = LoggerFactory.getLogger(DefaultMediaService.class);
	private final DefaultMediaServiceHelper helper;

	public DefaultMediaService(DefaultMediaServiceHelper helper) {
		this.helper = helper;
	}

	/**
	 * @param term is what we are looking for.
	 *
	 * @return combined media from both downstreams.
	 */
	@Override
	@Timed("get-all-media")
	public List<Media> getMedia(String term) {
		log.info("*************************");
		log.info("All media consuming started.");
		List<Media> mediaList = Collections.emptyList();
		// Executing two consumers in different threads and waiting for the response within 60 secs.
		// No response returning empty collection.
		CompletableFuture<List<Media>> mediaListFuture = helper
				.getGoogleBooks(term).thenCombine(helper
				.getItunesMusic(term),
					(books, albums) -> helper.convertToMedia(books, albums)
				)
				.exceptionally((e) -> Collections.emptyList())
				.completeOnTimeout(Collections.emptyList(), 60, TimeUnit.SECONDS);

		try {
			mediaList = mediaListFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception occurred: " + e);
		} finally {
			// in any case better to go with the cancelling of future
			mediaListFuture.cancel(true);
		}
		log.info("All media consuming finished.");
		return mediaList;
	}
}
