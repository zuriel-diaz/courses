package com.zurieldiaz.courses.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.PaymentType;
import com.zurieldiaz.courses.repository.PaymentTypeRepository;

@RestController
public class PaymentTypeController {

	private final PaymentTypeRepository paymentTypeRepository;
	
	public PaymentTypeController(PaymentTypeRepository paymentTypeRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
	}
	
	@GetMapping("/paymentTypes")
	public List<PaymentType> getAll(){
		return this.paymentTypeRepository.findAll();
	}
	
	@GetMapping("/paymentTypes/{paymentTypeId}")
	public PaymentType findOne(@PathVariable(value = "paymentTypeId") Long paymentTypeId) {
		return this.paymentTypeRepository.findById(paymentTypeId).orElse(null);
	}
	
	@PostMapping("/paymentTypes")
	public PaymentType savePaymentType(@Valid @RequestBody PaymentType paymentType){
		return this.paymentTypeRepository.save(paymentType);
	}
	
	@DeleteMapping("/paymentTypes/{paymentTypeId}")
	public ResponseEntity<?> deletePaymentType(@PathVariable(value = "paymentTypeId") Long paymentTypeId){
		PaymentType paymentType = this.paymentTypeRepository.findById(paymentTypeId).orElse(null);
		
		if(paymentType == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
}