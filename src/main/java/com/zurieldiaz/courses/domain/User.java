package com.zurieldiaz.courses.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "last_name")
	private String lastName;
	
	private String genre;
	
	@NotNull
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	private String bio;
	
	@Size(max = 150)
	private String email;
	
	@Size(max = 200)
	private String password;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Size(max = 70)
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@OneToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToMany(mappedBy="user")
	private List<Address> addresses;
	
	@OneToMany(mappedBy="user")
	private List<PaymentMethod> paymentMethods;
	
	@OneToMany(mappedBy = "user")
	private List<Course> courses;
	
	@OneToMany(mappedBy = "user")
	private List<Purchase> purchases;
	
	@OneToMany(mappedBy = "user")
	private List<Enrollment> enrollments;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;
	
	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
}