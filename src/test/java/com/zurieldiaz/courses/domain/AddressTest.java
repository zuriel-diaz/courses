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

import com.zurieldiaz.courses.repository.AddressRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AddressTest {
	
	private TestEntityManager testEntityManager;
	private UserRepository userRepository;
	private AddressRepository addressRepository;
	private Validator validator;
	private Address address;
	private User user;
	
	@Before
	public void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.usingContext().getValidator();
		this.user = this.userRepository.findById(1L).orElse(null);
		this.address = this.createAddress();
	}
	
	@Test
	public void verifyUserExists() {
		assertThat(this.user).isNotNull();
	}
	
	@Test
	public void verifyAddressCreationWithValidData() {
		Set<ConstraintViolation<Address>> violations = this.validator.validate(address);
		assertThat(violations).isEmpty();
		Address savedAddress = this.addressRepository.save(address);
		this.testEntityManager.flush();
		assertThat(savedAddress).isNotNull();
		assertThat(savedAddress.getId()).isGreaterThan(0L);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void verifyAddressCreationWithAnEmptyZipCode() {
		this.address.setZipCode("");
		this.addressRepository.save(this.address);
		this.testEntityManager.flush();		
	}
	
	private Address createAddress() {
		Address address = new Address();
		address.setCountry("mexico");
		address.setState("mexico");
		address.setCity("cdmx");
		address.setSuburb("aztecas");
		address.setZipCode( "1111111" );
		address.setStreet("springworld");
		address.setExternalNumber(1);
		address.setInternalNumber(1);
		address.setUser(this.user);
		return address;
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
	public void setAddressRepository(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

}