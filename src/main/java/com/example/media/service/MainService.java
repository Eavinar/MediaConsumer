package com.example.media.service;

import com.example.media.entity.Media;

import java.util.List;

public interface MainService {
	/**
	 * @param term is what we are looking for.
	 *
	 * @return returns media from downstreams.
	 */
	List<Media> getMedia(String term);
}
