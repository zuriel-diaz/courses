package com.zurieldiaz.courses.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zurieldiaz.courses.domain.Course;
import com.zurieldiaz.courses.domain.Enrollment;
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.EnrollmentRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class EnrollmentController {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final String STUDENT_ROLE = "student";
	
	public EnrollmentController(
			UserRepository userRepository,
			CourseRepository courseRepository,
			EnrollmentRepository enrollmentRepository
			) {
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.enrollmentRepository = enrollmentRepository;
	}
	
	@GetMapping("/enrollments/student/{studentId}")
	public List<Enrollment> retrieveEnrollmentsPerStudent(@PathVariable(value = "studentId") Long studentId){
		User user = this.userRepository.findById(studentId).orElse(null);
		
		if(user == null || !user.getRole().getName().equalsIgnoreCase(STUDENT_ROLE)) {
			return null;
		}
		
		return this.enrollmentRepository.findByUserId(studentId);
	}
	
	@PostMapping("/enrollments/course/{courseId}/student/{studentId}")
	public ResponseEntity<?> saveEnrollment(
			@PathVariable(value = "courseId") Long courseId,
			@PathVariable(value = "studentId") Long studentId,
			@Valid @RequestBody Enrollment enrollment
			){
		User user = this.userRepository.findById(studentId).orElse(null);
		Course course = this.courseRepository.findById(courseId).orElse(null);
		
		if( user == null || course == null ) {
			return ResponseEntity.badRequest().build();
		}
		
		enrollment.setCourse(course);
		enrollment.setUser(user);
		
		this.enrollmentRepository.save(enrollment);
		return ResponseEntity.ok().build();
	}
	
}