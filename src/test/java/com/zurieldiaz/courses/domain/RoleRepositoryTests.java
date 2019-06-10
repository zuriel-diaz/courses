package com.zurieldiaz.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.reset;
import com.zurieldiaz.courses.repository.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RoleRepositoryTests {

	@MockBean
	private RoleRepository roleRepository;
	
	@Before
	public void setupRoleRepositoryMock() {
		given(this.roleRepository.count()).willReturn(4L);
	}
	
	@Test
	public void verifyRolesExists() {
		assertThat(this.roleRepository.count()).isEqualTo(4L);
	}
	
	@After
	public void resetRoleRepositoryMock() {
		reset(this.roleRepository);
	}
	
}