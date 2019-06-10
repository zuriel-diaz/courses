package com.zurieldiaz.courses.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.zurieldiaz.courses.domain.Role;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class JpaRoleRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testRoleEntityBinding() {
		Long id = entityManager.persistAndGetId(this.createRole(),Long.class);
		Role role = this.roleRepository.findById(id).get();
		
		assertThat(role.getName()).isEqualTo("web");
	}

	private Role createRole() {
		return new Role("web",true);
	}
	
}
