package com.zurieldiaz.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zurieldiaz.courses.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	
}
