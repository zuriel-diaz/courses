package com.zurieldiaz.courses.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Address;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.AddressRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class AddressController {

	private UserRepository userRepository;
	
	private AddressRepository addressRepository;
	
	public AddressController(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}
	
	@GetMapping("/users/{userId}/addresses")
	public List<Address> getAll(@PathVariable(value = "userId") Long userId){
		
		User user = this.userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			return null;
		}
		
		return this.addressRepository.findByUserId(userId);
	}
	
	@PostMapping("/users/{userId}/addresses")
	public ResponseEntity<?> saveAddress(@PathVariable(value = "userId") Long userId, @Valid @RequestBody Address address){
		User user = this.userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		address.setUser(user);
		this.addressRepository.save(address);
		return ResponseEntity.ok().build();
	}
	
	
	
}
