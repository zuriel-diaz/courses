package com.zurieldiaz.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.zurieldiaz.courses.repository.RoleRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserTest {

	private TestEntityManager entityManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private Validator validator;
	private Role role;

	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		role = this.roleRepository.findById(2L).orElse(null);
	}
	
	@Test
	public void verifyStudentRoleExist(){
		assertThat(role).isNotNull();
	}
	
	@Test
	public void createUserWithValidData() {
		User validUser = this.getUser(true);
		Set<ConstraintViolation<User>> violations = this.validator.validate(validUser);
		assertThat(violations).isEmpty();
		User savedUser = this.entityManager.persistAndFlush(validUser);
		assertThat(savedUser.getId()).isNotZero();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void createUserWithEmptyEmail() {
		User invalidUser = this.getUser(false);
		this.userRepository.save(invalidUser);
		this.entityManager.flush();
	}
	
	private User getUser(boolean valid) {
		User user = new User();
		user.setFirstName("user");
		user.setLastName("lastName");
		user.setGenre("M");
		user.setBirthDate(Date.valueOf(LocalDate.parse("10-10-1980", DateTimeFormatter.ofPattern("dd-MM-uuuu"))));
		user.setEmail( (valid) ? "user@test.com" : "" );
		user.setRole(role);
		return user;
	}
	
	@Autowired
	public void setTestEntityManager(TestEntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
}
