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
import org.springframework.test.context.junit4.SpringRunner;

import com.zurieldiaz.courses.repository.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RoleTest {

	private TestEntityManager testEntityManager;
	private RoleRepository roleRepository;
	private Validator validator;
	private Role role;
	
	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		role = new Role();
	}
	
	@Test
	public void createRoleWithValidData() {
		role.setName("default");
		Set<ConstraintViolation<Role>> violations = this.validator.validate(role);
		assertThat(violations).isEmpty();
		Role savedRole = this.roleRepository.save(role);
		this.testEntityManager.flush();
		assertThat(savedRole).isNotNull();
		assertThat(savedRole.getId()).isNotZero();
	}
	
	@Test(expected = ConstraintViolationException.class) 
	public void verifyRoleCreationWithAnEmptyName() {
		role.setName("");
		this.roleRepository.save(role);
		this.testEntityManager.flush();
	}
	
	@Autowired
	public void setTestEntityManager(TestEntityManager testEntityManager) {
		this.testEntityManager = testEntityManager;
	}
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
}