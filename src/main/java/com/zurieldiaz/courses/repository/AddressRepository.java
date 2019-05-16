package com.zurieldiaz.courses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zurieldiaz.courses.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByUserId(Long userId);
	
}
