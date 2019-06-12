package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleControllerTest {
	
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
		
	@LocalServerPort
	private int port;
	
	@Autowired
	public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}
	
	@Before
	public void setupMockMvc() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void retrieveAll() throws Exception {
		this.mockMvc.perform(get("/api/roles").contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
}
