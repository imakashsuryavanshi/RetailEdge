package com.enterprise.retailedge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.retailedge.dto.UserDTO;
import com.enterprise.retailedge.dto.UserResponseDTO;
import com.enterprise.retailedge.model.User;
import com.enterprise.retailedge.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
	    UserResponseDTO savedUser = userService.createUser(userDTO);
	    return ResponseEntity.ok(savedUser);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserDTO userDTO) {
	    UserResponseDTO updatedUser = userService.updateUser(userId, userDTO);
	    return ResponseEntity.ok(updatedUser);
	}
	
	//Get all Users
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	//Get a user by Id
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable UUID userId){
		Optional<User> user = userService.getUserById(userId);
		return user.map(ResponseEntity :: ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	 // Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
	
}
