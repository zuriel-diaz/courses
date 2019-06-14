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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.RoleRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;	
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean 
	private RoleRepository roleRepository;
	
	private Role role;
	private User user;
	
	@Before
	public void setup() {
		role = this.createRole();
		user = this.createUser(role);
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		
		Mockito.when(this.userRepository.findAll()).thenReturn(users);
		Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
	}
	
	@Test
	public void createUserWithValidDataApi() throws Exception{
		this.user.setId(5L);
		this.user.setRole(null);
		this.mockMvc.perform(
				post("/roles/{roleId}/users",2)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.user)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void createUserWhenEmailIsEmptyThenReturnBadRequest() throws Exception{
		this.user.setEmail("");
		this.user.setRole(null);
		this.mockMvc.perform(
				post("/roles/{roleId}/users",2)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.user)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void retrieveAllUsers() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders 
				.get("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}
	
	@Test
	public void retrieveUserById() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/users/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}
	
	@Test
	public void verifyUserDataInBothLayers() throws Exception {
		this.mockMvc.perform(
				get("/users/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(this.user.getEmail()));
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setName("teacher");
		role.setActive(true);
		return role;
	}
	
	private User createUser(Role role) {
		User user = new User();
		user.setId(1L);
		user.setFirstName("user");
		user.setLastName("default");
		user.setGenre("M");
		user.setBirthDate(Date.valueOf(LocalDate.parse("1981-01-01")));
		user.setEmail("default_user@gmail.com");
		user.setPassword("123456");
		user.setBio("default bio.");
		user.setPhoneNumber("333-2122454");
		user.setRole(role);
		return user;
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