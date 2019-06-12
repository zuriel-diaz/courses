package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zurieldiaz.courses.domain.PaymentType;
import com.zurieldiaz.courses.repository.PaymentTypeRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers=PaymentTypeController.class)
public class PaymentTypeControllerTest {

	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@MockBean
	private PaymentTypeRepository paymentTypeRepository;
	
	@Test
	public void testPaymentTypeApi() throws Exception{
		this.mockMvc.perform(get("/paymentTypes")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk());			
	}
	
	@Test
	public void whenNullValueThenReturns400() throws Exception{
		PaymentType paymentType = new PaymentType(0L, "");
		this.mockMvc.perform(post("/paymentTypes")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(this.objectMapper.writeValueAsString(paymentType)))
				.andExpect(status().isBadRequest());		
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
