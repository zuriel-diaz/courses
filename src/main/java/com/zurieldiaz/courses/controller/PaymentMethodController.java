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

import com.zurieldiaz.courses.domain.PaymentMethod;
import com.zurieldiaz.courses.domain.PaymentType;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.PaymentMethodRepository;
import com.zurieldiaz.courses.repository.PaymentTypeRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class PaymentMethodController {

	private final PaymentTypeRepository paymentTypeRepository;
	private final PaymentMethodRepository paymentMethodRepository;
	private final UserRepository userRepository;
	
	public PaymentMethodController(
			PaymentTypeRepository paymentTypeRepository, 
			PaymentMethodRepository paymentMethodRepository, 
			UserRepository userRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
		this.paymentMethodRepository = paymentMethodRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/users/{userId}/paymentMethods")
	public List<PaymentMethod> getAll(@PathVariable(value = "userId") Long userId){
		User user = this.userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			return null;
		}
		
		return this.paymentMethodRepository.findByUserId(userId);
	}
	
	@PostMapping("/users/{userId}/paymentTypes/{paymentType}/paymentMethods")
	public PaymentMethod addPaymentMethod(
			@PathVariable(value = "userId") Long userId, 
			@PathVariable(value = "paymentType") Long paymentTypeId,
			@Valid @RequestBody PaymentMethod paymentMethod) {
		User user = this.userRepository.findById(userId).orElse(null);
		PaymentType paymentType = this.paymentTypeRepository.findById(paymentTypeId).orElse(null);
		
		if( user == null || paymentType == null ) {
			return null;
		}

		paymentMethod.setPaymentType(paymentType);
		paymentMethod.setUser(user);
		
		return this.paymentMethodRepository.save(paymentMethod);
	}
	
	@DeleteMapping("/paymentMethods/{paymentMethodId}")
	public ResponseEntity<?> deletePaymentMethod(@PathVariable(value = "paymentMethodId") Long paymentMethodId){
		PaymentMethod paymentMethod = this.paymentMethodRepository.findById(paymentMethodId).orElse(null);
		
		if(paymentMethod == null) {
			return ResponseEntity.notFound().build();
		}
		
		this.paymentMethodRepository.delete(paymentMethod);
		return ResponseEntity.ok().build();
	}
	
}