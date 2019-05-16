package com.zurieldiaz.courses.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zurieldiaz.courses.domain.Role;
import com.zurieldiaz.courses.repository.RoleRepository;

@RestController
public class RoleController {

	private final RoleRepository roleRepository;
	
	public RoleController(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@GetMapping("/roles")
	public List<Role> getAll(){
		return this.roleRepository.findAll();
	}
	
	@PostMapping("/roles")
	public Role addRole(@Valid @RequestBody Role role) {
		return this.roleRepository.save(role);
	}
	
	@GetMapping("/roles/{id}")
	public Optional<Role> findOne(@PathVariable Long id){
		return this.roleRepository.findById(id);
	}
	
	@PutMapping("/roles/{id}")
	public Role updateRole(@PathVariable Long id, @Valid @RequestBody Role roleRequest) {
		Role role = this.roleRepository.findById(id).orElse(null);
		if(role == null) {
			return null;
		}
		
		role.setActive(roleRequest.isActive());
		role.setName(roleRequest.getName());
		return this.roleRepository.save(role);
	}
	
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Long id){
		Role role = this.roleRepository.findById(id).orElse(null);
		
		if(role == null) {
			return ResponseEntity.notFound().build();
		}
		
		this.roleRepository.delete(role);
		return ResponseEntity.ok().build();
	}
	
}