package com.zurieldiaz.courses.controller;

import java.util.List;

import javax.persistence.Table;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.domain.Purchase;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.PurchaseRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
@Table(name = "purchases")
public class PurchaseController {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final PurchaseRepository purchaseRepository;
	
	public PurchaseController( 
			UserRepository userRepository, 
			CourseRepository courseRepository, 
			PurchaseRepository purchaseRepository ) {
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.purchaseRepository = purchaseRepository;
	}
	
	@GetMapping("/users/{userId}/purchases")
	public List<Purchase> getAllPerUser(@PathVariable(value = "userId") Long userId){
		if(!this.userRepository.existsById(userId)) {
			return null;
		}
		
		return this.purchaseRepository.findByUserId(userId);
	}
	
	@PostMapping("/users/{userId}/courses/{courseId}/purchases")
	public ResponseEntity<?> addPurchase(
			@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "courseId") Long courseId,
			@Valid @RequestBody Purchase purchase
			){
		User user = this.userRepository.findById(userId).orElse(null);
		Course course = this.courseRepository.findById(courseId).orElse(null);
		
		if(user == null || course == null) {
			return ResponseEntity.notFound().build();
		}
		
		purchase.setUser(user);
		purchase.setCourse(course);
		this.purchaseRepository.save(purchase);
		return ResponseEntity.ok().build();
	}
	
}