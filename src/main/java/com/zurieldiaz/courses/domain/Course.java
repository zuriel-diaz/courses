package com.zurieldiaz.courses.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max = 150)
	private String name;
	
	@NotBlank
	@Size(max = 150)
	private String title;
	
	@NotBlank
	private String description;
	
	private double price;
	
	@Column(name = "published_at")
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date publishedAt;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Size(max = 20)
	@Column(name = "level")
	private String level;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "course")
	private List<Purchase> purchases;
	
	@OneToMany(mappedBy = "course")
	private List<Enrollment> enrollments;
	
	@OneToMany(mappedBy = "course")
	private List<Review> reviews;
	
	public Course() {
		
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	
}