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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zurieldiaz.courses.domain.PaymentMethod;
import com.zurieldiaz.courses.domain.PaymentType;
import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.PaymentMethodRepository;
import com.zurieldiaz.courses.repository.PaymentTypeRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PaymentMethodController.class)
public class PaymentMethodControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private PaymentMethod paymentMethod;
	private PaymentType paymentType;
	private User user;
	private Role role;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PaymentTypeRepository paymentTypeRepository;
	
	@MockBean
	private PaymentMethodRepository paymentMethodRepository;
	
	@Before
	public void setUp() {
		this.paymentType = this.createPaymentType();
		this.role = this.createRole();
		this.user = this.createUser();
		this.paymentMethod = createPaymentMethod();
	
		this.user.setRole(role);
		this.paymentMethod.setUser(user);
		this.paymentMethod.setPaymentType(paymentType);
		
		ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
		paymentMethods.add(paymentMethod);
		
		Mockito.when(this.paymentMethodRepository.findById(this.paymentMethod.getId())).thenReturn(Optional.of(this.paymentMethod));
		Mockito.when(this.userRepository.findById(this.user.getId())).thenReturn(Optional.of(this.user));
		Mockito.when(this.paymentMethodRepository.findByUserId(this.user.getId())).thenReturn(paymentMethods);
	}
	
	@Test
	public void verifyPaymentMethodCreationWithValidData() throws JsonProcessingException, Exception {
		this.mockMvc.perform(
				post("/users/{userId}/paymentTypes/{paymentType}/paymentMethods",1L,1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(this.paymentMethod)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void verifyRetrieveAllPaymentMethodsByUser() throws Exception {
		this.mockMvc.perform(
				get("/users/{userId}/paymentMethods",this.user.getId())
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
	
	private PaymentMethod createPaymentMethod() {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setId(1L);
		paymentMethod.setClabe("123412341234123456");
		paymentMethod.setCardNumber("1234123443214321");
		paymentMethod.setCardHolderName("Guerrero Azteca");
		return paymentMethod;
	}
	
	private User createUser() {
		User user = new User();
		user.setId(1L);
		user.setFirstName("user");
		user.setLastName("default");
		user.setGenre("M");
		user.setBirthDate(Date.valueOf(LocalDate.parse("1981-01-01")));
		user.setBio("bio...");
		user.setEmail("user@test.com");
		user.setPassword("123456");
		user.setActive(true);
		user.setPhoneNumber("333-223-1234");
		return user;
	}
	
	private PaymentType createPaymentType() {
		PaymentType paymentType = new PaymentType();
		paymentType.setId(1L);
		paymentType.setName("tdc");
		return paymentType;
	}
	
	private Role createRole() {
		Role role = new Role();
		role.setId(1L);
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