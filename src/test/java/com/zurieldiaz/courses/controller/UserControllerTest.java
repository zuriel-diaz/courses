package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean 
	private RoleRepository roleRepository;
	
	@Before
	public void setup() {
		Role role = new Role("teacher",true);
		User user = new User(1L,"user","default","M",Date.valueOf(LocalDate.parse("1989-01-01")),"user@me.com","12345","default",true,"333-123-456",role);
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		
		Mockito.when(this.userRepository.findAll()).thenReturn(users);
		Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
	}
	
	@Test
	public void retrieveAllUsers() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders 
				.get("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}
	
	@Test
	public void getUserById() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/users/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}
	
	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
}
