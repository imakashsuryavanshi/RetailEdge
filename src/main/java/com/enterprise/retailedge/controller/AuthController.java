package com.enterprise.retailedge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.retailedge.service.AuthService;
import com.enterprise.retailedge.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password"))
			);
		
		UserDetails userDetails = authService.loadUserByUsername(loginRequest.get("username"));
		String token = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(Map.of("token", token));
	}
}
