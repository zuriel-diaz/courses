package com.zurieldiaz.courses;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.zurieldiaz.courses.repository.PaymentTypeRepository;
import com.zurieldiaz.courses.repository.RoleRepository;

@Transactional
@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CoursesApplicationTests {
	
	private RoleRepository roleRepository;
	private PaymentTypeRepository paymentTypeRepository;
	
	@Test
	public void validateNumberOfRoles() {
		assertEquals(3,this.roleRepository.count());
	}
	
	@Test
	public void validateNumbersOfPaymentTypes() {
		assertEquals(2,this.paymentTypeRepository.count());
	}
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;;
	}
	
	@Autowired
	public void setPaymentTypeRepository(PaymentTypeRepository paymentTypeRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
	}
}