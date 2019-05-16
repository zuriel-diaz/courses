package com.zurieldiaz.courses.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(max = 200)
	private String comment;
	
	private int rating;
	
	@Column(name = "reviewed_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reviewedAt;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public Review() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(Date reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}