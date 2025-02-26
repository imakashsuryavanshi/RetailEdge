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

import com.enterprise.retailedge.model.User;
import com.enterprise.retailedge.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//Create a new user
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User savedUser = userService.createUser(user);
		return ResponseEntity.ok(savedUser);
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
	
	//Update a user
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User updatedUser){
		try{
			User updated = userService.updateUser(userId, updatedUser);
			return ResponseEntity.ok(updated);
		}catch(RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	 // Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
	
}
