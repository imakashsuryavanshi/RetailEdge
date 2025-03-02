package com.enterprise.retailedge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enterprise.retailedge.dto.UserDTO;
import com.enterprise.retailedge.dto.UserResponseDTO;
import com.enterprise.retailedge.model.User;
import com.enterprise.retailedge.repository.UserRepository;

@Service
public class UserService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	//Create a new User
	public UserResponseDTO createUser(UserDTO userDTO) {
		User user = new User();
	    user.setUsername(userDTO.getUsername());
	    user.setPassword(userDTO.getPassword());
	    user.setName(userDTO.getName());
	    user.setEmail(userDTO.getEmail());
	    user.setActive(userDTO.isActive());
	    
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRole = authentication.getAuthorities().iterator().next().getAuthority();
		
		if(user.getUsername() == "SUPERADMIN") {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(user);
			return mapToUserResponseDTO(savedUser);
		}
		
		// Admin can create Managers and Employees
        if (currentUserRole.equals("ROLE_ADMIN")) {
            if (user.getRole() == User.Role.MANAGER || user.getRole() == User.Role.EMPLOYEE) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userRepository.save(user);
    			return mapToUserResponseDTO(savedUser);
            } else {
                throw new RuntimeException("Admin can only create Managers and Employees.");
            }
        }
        // Manager can create Employees only
        else if (currentUserRole.equals("ROLE_MANAGER")) {
            if (user.getRole() == User.Role.EMPLOYEE) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userRepository.save(user);
    			return mapToUserResponseDTO(savedUser);
            } else {
                throw new RuntimeException("Manager can only create Employees.");
            }
        }
        // Employee cannot create any users
        else {
            throw new RuntimeException("Employee cannot create users.");
        }
}
	
	
	//Get all Users
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	//Get User by Id
	public Optional<User> getUserById(UUID userId) {
		return userRepository.findById(userId);
	}
	
	//Get User by username
		public Optional<User> getUserById(String username) {
			return userRepository.findByUsername(username);
		}
	
	//Delete a user
	public void deleteUser(UUID userId) {
		userRepository.deleteById(userId);
	}
	
	//Update a user
	public UserResponseDTO updateUser(UUID userId, UserDTO userDTO) {
		return userRepository.findById(userId).map(user -> {
	        user.setUsername(userDTO.getUsername());
	        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	        user.setName(userDTO.getName());
	        user.setEmail(userDTO.getEmail());
	        user.setActive(userDTO.isActive());
	        User updatedUser = userRepository.save(user);
	        return mapToUserResponseDTO(updatedUser);
		}).orElseThrow(() -> new RuntimeException("User not found with Id: " + userId));
	}

	private UserResponseDTO mapToUserResponseDTO(User user) {
	    UserResponseDTO userResponseDTO = new UserResponseDTO();
	    userResponseDTO.setUserId(user.getUserId());
	    userResponseDTO.setUsername(user.getUsername());
	    userResponseDTO.setName(user.getName());
	    userResponseDTO.setEmail(user.getEmail());
	    userResponseDTO.setActive(user.isActive());
	    return userResponseDTO;
	}
	
}
