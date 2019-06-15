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

import com.zurieldiaz.courses.repository.PaymentTypeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PaymentTypeTest {

	private TestEntityManager testEntityManager;
	private PaymentTypeRepository paymentTypeRepository;
	private Validator validator;
	private PaymentType paymentType;
	
	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		paymentType = new PaymentType();
	}
	
	@Test
	public void creatPaymentTypeWithValidData() {
		paymentType.setName("tdd");
		Set<ConstraintViolation<PaymentType>> violations = this.validator.validate(paymentType);
		assertThat(violations).isEmpty();
		PaymentType paymentTypeSaved = this.testEntityManager.persistAndFlush(paymentType);
		assertThat(paymentTypeSaved.getId()).isNotZero();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void createPaymentTypeWithAnEmptyName() {
		paymentType.setName("");
		this.paymentTypeRepository.save(paymentType);
		this.testEntityManager.flush();
	}
	
	@Autowired 
	public void setTestEntityManager(TestEntityManager testEntityManager) {
		this.testEntityManager = testEntityManager;
	}
	
	@Autowired
	public void setPaymentTypeRepository(PaymentTypeRepository paymentTypeRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
	}
	
}
