package com.zurieldiaz.courses.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String country;
	
	@NotEmpty
	private String state;
	
	@NotEmpty
	private String city;
	
	private String suburb;
	
	@NotEmpty
	@Column(name = "zipcode")
	private String zipCode;
	
	@NotEmpty
	private String street;
	
	@Column(name = "external_number")
	private int externalNumber;
	
	@Column(name = "internal_number")
	private int internalNumber;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Address() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getExternalNumber() {
		return externalNumber;
	}

	public void setExternalNumber(int externalNumber) {
		this.externalNumber = externalNumber;
	}

	public int getInternalNumber() {
		return internalNumber;
	}

	public void setInternalNumber(int internalNumber) {
		this.internalNumber = internalNumber;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}