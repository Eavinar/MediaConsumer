package com.example.media;

import com.example.media.entity.GoogleBooks;
import com.example.media.entity.ItunesMusic;
import com.example.media.entity.Media;
import com.example.media.helper.DefaultMediaServiceHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultMediaServiceHelperTest {
	@Autowired
	DefaultMediaServiceHelper helper;

	private GoogleBooks googleBooks;
	private ItunesMusic itunesMusic;

	@Before
	public void setUp() {
		googleBooks = new GoogleBooks();

		GoogleBooks.Item googleBook1 = new GoogleBooks.Item();
		GoogleBooks.VolumeInfo volumeInfo1 = new GoogleBooks.VolumeInfo();
		volumeInfo1.setTitle("One");
		googleBook1.setVolumeInfo(volumeInfo1);

		GoogleBooks.Item googleBook2 = new GoogleBooks.Item();
		GoogleBooks.VolumeInfo volumeInfo2 = new GoogleBooks.VolumeInfo();
		volumeInfo2.setTitle("Five");
		googleBook2.setVolumeInfo(volumeInfo2);
		googleBooks.setItems(Arrays.asList(googleBook1, googleBook2));

		itunesMusic = new ItunesMusic();

		ItunesMusic.Item itunesMusic1 = new ItunesMusic.Item();
		itunesMusic1.setTitle("Six");

		ItunesMusic.Item itunesMusic2 = new ItunesMusic.Item();
		itunesMusic2.setTitle("Two");
		itunesMusic.setItems(Arrays.asList(itunesMusic1, itunesMusic2));
	}

	@Test
	public void convertToMediaTest() {
		List<Media> mediaList = helper.convertToMedia(googleBooks, itunesMusic);
		assertThat(mediaList).isNotNull();
		assertThat(mediaList.size()).isEqualTo(4);
		assertThat(mediaList.get(0).getType()).isEqualToIgnoringCase("Book");
		assertThat(mediaList.get(2).getType()).isEqualToIgnoringCase("Album");
		assertThat(mediaList.get(1).getTitle()).isEqualToIgnoringCase("One");
		assertThat(mediaList.get(3).getTitle()).isEqualToIgnoringCase("Two");
	}
}
