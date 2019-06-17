package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zurieldiaz.courses.domain.Address;
import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.AddressRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean 
	private AddressRepository addressRepository;
	
	private Address address;
	private Role role;
	private User user;
	
	@Before
	public void setUp() {
		this.role = this.createRole();
		this.user = this.createUser(role);
		this.address = this.createAddress();
		ArrayList<Address> addresses = new ArrayList<>();
		addresses.add(address);
		Mockito.when(this.userRepository.findById(this.user.getId())).thenReturn(Optional.of(this.user));
		Mockito.when(this.addressRepository.findByUserId(this.user.getId())).thenReturn(addresses);
	}
	
	@Test
	public void verifyAddressCreationWithValidaData() throws Exception{
		this.mockMvc.perform(
				post("/users/{userId}/addresses", this.user.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.address)))
				.andExpect(status().isOk());
	}
	
	@Test 
	public void verifyAddressCreationWithEmptyZipCode() throws Exception{
		this.address.setZipCode("");
		this.mockMvc.perform(
				post("/users/{userId}/addresses", this.user.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.address)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void verifyRetrieveAddressesByUser() throws Exception {
		this.mockMvc.perform(
				get("/users/{userId}/addresses", this.user.getId())
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("default");
		role.setActive(true);
		return role;
	}
	
	private User createUser(Role role) {
		User user = new User();
		user.setId(1L);
		user.setFirstName("mike");
		user.setLastName("cruz");
		user.setBio("bio");
		user.setBirthDate(Date.valueOf(LocalDate.parse("1981-01-01")));
		user.setGenre("M");
		user.setEmail("mike@test.com");
		user.setPassword("12345@45");
		user.setActive(true);
		user.setRole(role);
		return user;
	}
	
	private Address createAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setCountry("mexico");
		address.setState("mexico");
		address.setCity("mexico_city");
		address.setSuburb("suburb 1");
		address.setZipCode("123123");
		address.setStreet("springworld");
		address.setExternalNumber(1);
		address.setInternalNumber(1);
		return address;
	}
	
	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
}