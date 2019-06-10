package com.zurieldiaz.courses;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.zurieldiaz.courses.repository.RoleRepository;

@Transactional
@Sql(scripts = "classpath:/test-data.sql")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoursesApplicationTests {
	
	private RoleRepository roleRepository;
	
	@Test
	public void validateNumberOfRoles() {
		assertEquals(3,this.roleRepository.count());
	}
	
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;;
	}
	
}