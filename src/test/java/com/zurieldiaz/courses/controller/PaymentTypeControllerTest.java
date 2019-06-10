package com.zurieldiaz.courses.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.zurieldiaz.courses.repository.PaymentTypeRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers=PaymentTypeController.class)
public class PaymentTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PaymentTypeRepository paymentTypeRepository;
	
	@Test
	public void testPaymentTypeApi() throws Exception{
		this.mockMvc.perform(get("/paymentTypes")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
			
	}
	
}
