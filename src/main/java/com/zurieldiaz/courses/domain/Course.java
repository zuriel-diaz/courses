package com.zurieldiaz.courses.domain;

public class Course {

	private long id;
	private String name;
	private String description;
	private String title;
	private double price;
	
	public Course(long id, String name, String description, String title, double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.title = title;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}