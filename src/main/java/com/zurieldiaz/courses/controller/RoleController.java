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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class RoleController {

	private final RoleRepository roleRepository;
	
	public RoleController(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@GetMapping("/roles")
	@ApiOperation(value = "Retrieve all roles.", notes = "Retrieve list of roles.")
	public List<Role> getAll(){
		return this.roleRepository.findAll();
	}
	
	@PostMapping("/roles")
	@ApiOperation(value = "Create role.", notes = "Create a new role.")
	public Role addRole(
			@ApiParam(required = true, name = "role", value = "New role")
			@Valid @RequestBody Role role) {
		return this.roleRepository.save(role);
	}
	
	@GetMapping("/roles/{id}")
	@ApiOperation(value = "Retrieve role.", notes = "Retrieve role by identifier.", response = Role.class)
	public Optional<Role> findOne(
			@ApiParam(required = true, name = "id", value = "role identifier.")
			@PathVariable Long id){
		return this.roleRepository.findById(id);
	}
	
	@PutMapping("/roles/{id}")
	@ApiOperation(value = "Update role.", notes = "Update role data.")
	public Role updateRole(
			@ApiParam(required = true, name = "id", value = "role identifier.")
			@PathVariable Long id, 
			@ApiParam(required = true, name = "role", value = "New role data.")
			@Valid @RequestBody Role roleRequest) {
		Role role = this.roleRepository.findById(id).orElse(null);
		if(role == null) {
			return null;
		}
		
		role.setActive(roleRequest.isActive());
		role.setName(roleRequest.getName());
		return this.roleRepository.save(role);
	}
	
	@DeleteMapping("/roles/{id}")
	@ApiOperation(value = "Delete role.", notes = "Delete role by identifier.")
	public ResponseEntity<?> deleteRole(
			@ApiParam(required = true, name = "id", value = "role identifier.")
			@PathVariable Long id){
		Role role = this.roleRepository.findById(id).orElse(null);
		
		if(role == null) {
			return ResponseEntity.notFound().build();
		}
		
		this.roleRepository.delete(role);
		return ResponseEntity.ok().build();
	}
	
}