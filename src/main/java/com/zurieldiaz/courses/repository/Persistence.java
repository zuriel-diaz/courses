package com.zurieldiaz.courses.repository;

import java.util.List;

import com.zurieldiaz.courses.domain.Course;

public interface Persistence {

	public List<Course> retrieveAll();
	
	public Course findOne(long id);
	
	public Course add(Course course);
	
}
