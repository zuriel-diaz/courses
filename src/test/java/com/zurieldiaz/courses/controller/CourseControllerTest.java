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
import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CourseController.class)
public class CourseControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	private User user;
	private Role role;
	private Course course;
	
	@MockBean 
	private UserRepository userRepository;
	
	@MockBean
	private CourseRepository courseRepository;

	@Before
	public void setUp() {
		this.role = createRole();
		this.user = createUser(role);
		this.course = createCourse();
		ArrayList<Course> courses = new ArrayList<>();
		courses.add(course);
		
		Mockito.when(this.userRepository.findById(this.user.getId())).thenReturn(Optional.of(this.user));
		Mockito.when(this.courseRepository.findById(this.course.getId())).thenReturn(Optional.of(course));
		Mockito.when(this.courseRepository.findAll()).thenReturn(courses);
	}
	
	@Test
	public void verifyCourseCreationWithValidData() throws Exception{
		this.mockMvc.perform(
				post("/users/{userId}/courses", this.user.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.course)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void verifyCourseCreationWithAnEmptyName() throws Exception{
		this.course.setName("");
		this.mockMvc.perform(
				post("/users/{userId}/courses", this.user.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.course)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void verifyRetrieveCourses() throws Exception{
		this.mockMvc.perform(
				get("/courses")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
	
	@Test
	public void verifyRetrieveCourseById() throws Exception {
		this.mockMvc.perform(
				get("/courses/{id}",1)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(this.course.getName()));
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("teacher");
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
	
	private Course createCourse() {
		Course course = new Course();
		course.setId(1L);
		course.setName("Backend development essentials.");
		course.setTitle("Backend Development Essentials");
		course.setDescription("Backend essentials...");
		course.setPrice(200.0);
		course.setLevel("basic");
		course.setActive(true);
		return course;
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
