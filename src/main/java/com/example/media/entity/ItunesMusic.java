package com.example.media.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"results",
})
public class ItunesMusic {
	private final String type = "Album";

	public String getType() {
		return type;
	}

	@JsonProperty("results")
	private List<Item> items = null;

	@JsonProperty("results")
	public List<Item> getItems() {
		return items;
	}

	@JsonProperty("results")
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
			"title",
			"author"
	})
	public static class Item {
		@JsonProperty("collectionName")
		private String title;

		@JsonProperty("artistName")
		private String author;

		@JsonProperty("artistName")
		public String getAuthor() {
			return author;
		}

		@JsonProperty("artistName")
		public void setAuthor(String author) {
			this.author = author;
		}

		@JsonProperty("collectionName")
		public String getTitle() {
			return title;
		}

		@JsonProperty("collectionName")
		public void setTitle(String title) {
			this.title = title;
		}
	}
}