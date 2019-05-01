package com.zurieldiaz.courses.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.zurieldiaz.courses.domain.Course;

@Repository
public class PersistenceImpl implements Persistence {

	private List<Course> courses;
	
	public PersistenceImpl() {
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
	
	@Override
	public List<Course> retrieveAll() {
		return this.courses;
	}

	@Override
	public Course findOne(long id) {
		for(Course course : this.courses) {
			if(course.getId() == id) { return course; }
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", null);
	}

	@Override
	public Course add(Course course) {
		this.courses.add(new Course(course.getId() ,course.getName(), course.getDescription(), course.getTitle(), course.getPrice() ));
		return course;
	}

}
