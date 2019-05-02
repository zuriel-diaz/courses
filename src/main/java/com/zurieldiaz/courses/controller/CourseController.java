package com.zurieldiaz.courses.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zurieldiaz.courses.domain.Course;

@RestController
@RequestMapping("/api")
public class CourseController {

	private List<Course> courses;
	
	public CourseController() {
		this.courses = new ArrayList<>();
		this.populateCourses();
	}
	
	private void populateCourses() {
		courses.add(new Course(1, "Course 1","Description 1","The new course 1",100.0));
		courses.add(new Course(2, "Course 2","Description 2","The new course 2",110.0));
		courses.add(new Course(3, "Course 3","Description 3","The new course 3",120.0));
		courses.add(new Course(4, "Course 4","Description 4","The new course 4",130.0));
		courses.add(new Course(5, "Course 5","Description 5","The new course 5",140.0));
	}
	
	@GetMapping("/courses")
	public List<Course> retrieveCourses() {
		return courses;
	}
	
	@PostMapping("/courses")
	public Course addCourse(@RequestBody Course course) {
		this.courses.add(new Course(course.getId() ,course.getName(), course.getDescription(), course.getTitle(), course.getPrice() ));
		return course;
	}
	
	@GetMapping("/courses/{id}")
	public Course findCourse(@PathVariable Long id) {
		for(Course course : this.courses) {
			if(course.getId() == id) { return course; }
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", null);
	}
	
}