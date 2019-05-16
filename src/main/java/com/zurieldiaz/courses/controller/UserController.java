package com.zurieldiaz.courses.controller;

import java.util.List;

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
import com.zurieldiaz.courses.domain.User;
import com.zurieldiaz.courses.repository.RoleRepository;
import com.zurieldiaz.courses.repository.UserRepository;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	public UserController(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@GetMapping("/users")
	public List<User> getAll(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/users/roles/{roleId}")
	public List<User> getUsersByRole(@PathVariable(value = "roleId") Long roleId){
		if( this.roleRepository.existsById(roleId) ) {
			return this.userRepository.findByRoleId(roleId);
		}
		
		return null;
	}
	
	@GetMapping("/users/{id}")
	public User findOne(@PathVariable(value="id") Long id) {
		return this.userRepository.findById(id).orElse(null);
	}
	
	@PostMapping("/roles/{roleId}/users")
	public User addUser(@PathVariable(value="roleId") Long roleId, @Valid @RequestBody User user) {
		Role role = this.roleRepository.findById(roleId).orElse(null);
		
		if(role == null) {
			return null;
		}
		
		user.setRole(role);
		return this.userRepository.save(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userRequest) {
		User user = this.userRepository.findById(id).orElse(null);
		
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		user.setBio(userRequest.getBio());
		user.setActive(userRequest.isActive());
		user.setPhoneNumber(userRequest.getPhoneNumber());
		
		this.userRepository.save(user);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
		User user = this.userRepository.findById(id).orElse(null);
		
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		this.userRepository.delete(user);
		return ResponseEntity.ok().build();
	}
	
}