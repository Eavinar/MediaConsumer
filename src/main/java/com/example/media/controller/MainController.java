package com.example.media.controller;

import com.example.media.entity.Media;
import com.example.media.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

	private final MainService mainService;

	@Autowired
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * @param term is what we are looking for.
	 * @return list of media including books and albums for downstreams.
	 */
	@GetMapping("/getMedia")
	public List<Media> getMedia(@RequestParam String term) {
		return mainService.getMedia(term);
	}
}
