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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.repository.CourseRepository;

@RestController
@RequestMapping("/api")
public class CourseController {

	private final CourseRepository courseRepository;
	
	public CourseController(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@GetMapping("/courses")
	public List<Course> retrieveCourses() {
		return this.courseRepository.findAll();
	}
	
	@PostMapping("/courses")
	public Course addCourse(@Valid @RequestBody Course course) {
		return this.courseRepository.save(course);
	}
	
	@GetMapping("/courses/{id}")
	public Optional<Course> findCourse(@PathVariable Long id) {
		return this.courseRepository.findById(id);
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
		Course course = this.courseRepository.getOne(id);
		this.courseRepository.deleteById(course.getId());		
		return ResponseEntity.ok().build();		
	}
}