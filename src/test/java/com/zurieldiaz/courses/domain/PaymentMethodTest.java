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

import com.zurieldiaz.courses.repository.PaymentMethodRepository;
import com.zurieldiaz.courses.repository.PaymentTypeRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PaymentMethodTest {

	private TestEntityManager testEntityManager;
	private UserRepository userRepository;
	private PaymentTypeRepository paymentTypeRepository;
	private PaymentMethodRepository paymentMethodRepository;
	private Validator validator;
	private PaymentType paymentType;
	private PaymentMethod paymentMethod;
	private User user;
	
	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		paymentType = this.paymentTypeRepository.findById(1L).orElse(null);
		user = this.userRepository.findById(1L).orElse(null);
		paymentMethod = this.createPaymentMethod();
	}
	
	@Test
	public void verifyIfPaymentTypeExists() {
		assertThat(paymentType).isNotNull();
		assertThat(paymentType.getId()).isGreaterThan(0L);
	}
	
	@Test
	public void verifyIfUserExists() {
		assertThat(user).isNotNull();
		assertThat(user.getId()).isGreaterThan(0L);
	}
	
	@Test
	public void verifyPaymentMethodCreationUsingValidData() {
		Set<ConstraintViolation<PaymentMethod>> violations = this.validator.validate(this.paymentMethod);
		assertThat(violations).isEmpty();
		PaymentMethod savedPaymentMethod =  this.paymentMethodRepository.save(this.paymentMethod);
		this.testEntityManager.flush();
		assertThat(savedPaymentMethod).isNotNull();
		assertThat(savedPaymentMethod.getId()).isGreaterThan(0L);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void verifyPaymentMethodCreationWithAnEmptyCardHolderName() {
		this.paymentMethod.setCardHolderName("");
		this.paymentMethodRepository.save(this.paymentMethod);
		this.testEntityManager.flush();
	}
	
	private PaymentMethod createPaymentMethod() {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setClabe("123412341234123412");
		paymentMethod.setCardNumber("1234123412341234");
		paymentMethod.setCardHolderName("guerrero azteca");
		paymentMethod.setActive(true);
		paymentMethod.setPaymentType(this.paymentType);
		paymentMethod.setUser(this.user);
		return paymentMethod;
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
	public void setPaymentTypeRepository(PaymentTypeRepository paymentTypeRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
	}
	
	@Autowired
	public void setPaymentMethodRepository(PaymentMethodRepository paymentMethodRepository) {
		this.paymentMethodRepository = paymentMethodRepository;
	}
}
