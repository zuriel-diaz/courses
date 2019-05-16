package com.zurieldiaz.courses.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class CourseController {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final String TEACHER_ROLE = "teacher";
	
	public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/courses")
	public List<Course> retrieveCourses() {
		return this.courseRepository.findAll();
	}
	
	@GetMapping("/courses/{id}")
	public Optional<Course> findCourse(@PathVariable Long id) {
		return this.courseRepository.findById(id);
	}
	
	@PostMapping("/users/{userId}/courses")
	public Course addCourse(
			@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody Course course) {
		
		User user = this.userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			ResponseEntity.notFound().build();
		}
		
		if( !user.getRole().getName().equalsIgnoreCase(TEACHER_ROLE)) {
			ResponseEntity.badRequest().build();
		}
		
		course.setUser(user);
		return this.courseRepository.save(course);
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
		Course course = this.courseRepository.findById(id).orElse(null);
		
		if(course == null) {
			return ResponseEntity.notFound().build();
		}
		
		this.courseRepository.deleteById(id);		
		return ResponseEntity.ok().build();		
	}
}