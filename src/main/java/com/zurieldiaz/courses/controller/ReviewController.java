package com.zurieldiaz.courses.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.domain.Review;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.ReviewRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class ReviewController {

	private static final String STUDENT_ROLE = "student";
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	
	public ReviewController(
			ReviewRepository reviewRepository,
			UserRepository userRepository,
			CourseRepository courseRepository
			) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
	}
	
	@GetMapping("/reviews/course/{courseId}")
	public List<Review> retrieveReviewsPerCourse(@PathVariable(value = "courseId") Long courseId){
		Course course = this.courseRepository.findById(courseId).orElse(null);
		
		if(course == null) {
			return null;
		}
		
		return this.reviewRepository.findByCourseId(courseId);
	}
	
	@GetMapping("/reviews/student/{studentId}")
	public List<Review> retrieveReviewsPerStudent(@PathVariable(value = "studentId") Long studentId){
		User user = this.userRepository.findById(studentId).orElse(null);
		
		if( user == null || !user.getRole().getName().equalsIgnoreCase(STUDENT_ROLE) ) {
			return null;
		}
		
		return this.reviewRepository.findByUserId(studentId);
	}
	
	@GetMapping("/reviews/student/{studentId}/course/{courseId}")
	public List<Review> retrieveReviewsPerCourseAndStudent(
			@PathVariable(value = "studentId") Long studentId,
			@PathVariable(value = "courseId") Long courseId
			){
		User user = this.userRepository.findById(studentId).orElse(null);
		Course course = this.courseRepository.findById(courseId).orElse(null);
		
		if( user == null || course == null ) {
			return null;
		}
		
		return this.reviewRepository.findByUserIdAndCourseId(studentId, courseId);
	}
	
	@PostMapping("/reviews/student/{studentId}/course/{courseId}")
	public Review addReview(
			@PathVariable(value = "studentId") Long studentId,
			@PathVariable(value = "courseId") Long courseId,
			@Valid @RequestBody Review review){
		User user = this.userRepository.findById(studentId).orElse(null);
		Course course = this.courseRepository.findById(courseId).orElse(null);
		
		if( user == null || course == null ) {
			return null;
		}
		
		review.setCourse(course);
		review.setUser(user);
		
		return this.reviewRepository.save(review);
	}
}
