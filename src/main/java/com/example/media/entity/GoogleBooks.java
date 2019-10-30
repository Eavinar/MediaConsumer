package com.example.media.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"items"
})
public class GoogleBooks {
	private final String type = "Book";

	public String getType() {
		return type;
	}

	@JsonProperty("items")
	private List<Item> items = null;

	@JsonProperty("items")
	public List<Item> getItems() {
		return items;
	}

	@JsonProperty("items")
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
			"volumeInfo",
	})
	public static class Item {
		@JsonProperty("volumeInfo")
		private VolumeInfo volumeInfo;

		@JsonProperty("volumeInfo")
		public VolumeInfo getVolumeInfo() {
			return volumeInfo;
		}

		@JsonProperty("volumeInfo")
		public void setVolumeInfo(VolumeInfo volumeInfo) {
			this.volumeInfo = volumeInfo;
		}

		public List<String> getAuthor(){
			List<String> authors =  Optional.ofNullable(volumeInfo.getAuthors())
					.orElseGet(Collections::emptyList);

			return authors;
		}

		public String getTitle(){
			return volumeInfo.getTitle();
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
			"title",
			"authors",
	})
	public static class VolumeInfo {
		@JsonProperty("title")
		private String title;

		@JsonProperty("authors")
		private List<String> authors = null;

		@JsonProperty("title")
		public String getTitle() {
			return title;
		}

		@JsonProperty("title")
		public void setTitle(String title) {
			this.title = title;
		}

		@JsonProperty("authors")
		public List<String> getAuthors() {
			return authors;
		}

		@JsonProperty("authors")
		public void setAuthors(List<String> authors) {
			this.authors = authors;
		}
	}
}