package com.example.media.entity;

import java.util.Comparator;

public class Media implements Comparable<Media> {
	private java.lang.String title;
	private java.lang.String type;
	private String authors;

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String author) {
		this.authors = author;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	@Override
	public int compareTo(Media o) {
		return Comparator.comparing(Media::getType).reversed()
				.thenComparing(Media::getTitle)
				.compare(this, o);
	}
}
