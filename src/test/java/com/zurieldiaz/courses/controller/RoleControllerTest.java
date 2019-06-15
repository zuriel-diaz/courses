package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.repository.RoleRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoleController.class)
public class RoleControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private Role role;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@Before
	public void setUp() {
		this.role = this.createRole();
		
		ArrayList<Role> roles = new ArrayList<>();
		roles.add(role);
		
		Mockito.when(this.roleRepository.findById(this.role.getId())).thenReturn(Optional.of(role));
	}
	
	@Test
	public void verifyRoleCreationWithValidDataViaApi() throws JsonProcessingException, Exception {
		this.mockMvc.perform(
				post("/roles")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.role)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void verifyRoleCreationWithAnEmptyName() throws Exception{
		this.role.setName("");
		this.mockMvc.perform(
				post("/roles")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.role)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void verifyRetrieveAllRoles() throws Exception {
		this.mockMvc.perform(
				get("/roles")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
	
	@Test
	public void verifyRetrieveRoleById() throws Exception {
		this.mockMvc.perform(
				get("/roles/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(this.role.getName()));
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setId(1L);
		role.setActive(true);
		role.setName("default");
		return role;
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