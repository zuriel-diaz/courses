package com.zurieldiaz.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.zurieldiaz.courses.repository.CourseRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CourseTest {

	private TestEntityManager testEntityManager;
	private UserRepository userRepository;
	private CourseRepository courseRepository;
	private Validator validator;
	private User user;
	private Course course;
	
	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		this.user = this.userRepository.findById(1L).orElse(null);
		this.course = this.createCourse();
	}
	
	@Test
	public void verifyIfUserExists() {
		assertThat(this.user).isNotNull();
	}
	
	@Test
	public void verifyCourseCreationWithValidData() {
		Set<ConstraintViolation<Course>> violations = this.validator.validate(course);
		assertThat(violations).isEmpty();
		Course savedCourse = this.courseRepository.save(course);
		this.testEntityManager.flush();
		assertThat(savedCourse).isNotNull();
		assertThat(savedCourse.getId()).isGreaterThan(0L);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void verifyCourseCreationWithAnEmptyName() {
		this.course.setName("");
		this.courseRepository.save(this.course);
		this.testEntityManager.flush();
	}
	
	private Course createCourse() {
		Course course = new Course();
		course.setName("Backend Development with Java");
		course.setTitle("Backend Development with Java and Spring");
		course.setDescription("Full course to create APIs, ETLs with Java and Spring's ecosystem.");
		course.setPrice(20.2);
		course.setUser(this.user);
		course.setLevel("intermediate");
		course.setActive(true);
		return course;
	}
	
	@Autowired
	public void setTestEntityManager(TestEntityManager testEntityManager) {
		this.testEntityManager = testEntityManager;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setCourseRepository(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
}