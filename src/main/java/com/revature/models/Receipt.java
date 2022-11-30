package com.revature.models;

import java.util.Arrays;
import java.util.Objects;

public class Receipt {
	private int id;
	private String title;
	private byte[] image;

	public Receipt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Receipt(int id, String title, byte[] image) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
	}

	public Receipt(String title, byte[] image) {
		super();
		this.title = title;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", title=" + title + ", image=" + Arrays.toString(image) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + Objects.hash(id, title);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		return id == other.id && Arrays.equals(image, other.image) && Objects.equals(title, other.title);
	}

}
