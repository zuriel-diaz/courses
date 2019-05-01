package com.zurieldiaz.courses.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.repository.Persistence;

@RestController
@RequestMapping("/api")
public class CourseController {

	private final Persistence persistence;
	
	public CourseController(Persistence persistence) {
		this.persistence = persistence;
	}
	
	@GetMapping("/courses")
	public List<Course> retrieveCourses() {
		return this.persistence.retrieveAll();
	}
	
	@PostMapping("/courses")
	public Course addCourse(@RequestBody Course course) {
		return this.persistence.add(course);
	}
	
	@GetMapping("/courses/{id}")
	public Course findCourse(@PathVariable Long id) {
		return this.persistence.findOne(id);
	}
	
}