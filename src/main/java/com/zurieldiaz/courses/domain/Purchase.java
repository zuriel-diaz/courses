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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "purchases")
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "total_amount")
	private double totalAmount;
	
	@Column(name = "purchased_at")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchased_at;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private User user;

	public Purchase() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getPurchased_at() {
		return purchased_at;
	}

	public void setPurchased_at(Date purchased_at) {
		this.purchased_at = purchased_at;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}