package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.AdminDetails;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserInfoService;


@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserInfoService service;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome, This endpoint is not secure.";
	}
	
	@GetMapping("/getuser/{id}")
	public Optional<UserInfo> getUser(@PathVariable int id) {
		return service.getUser(id);
	   
	}
	
	@PostMapping("/createAdmin")
	public Optional<AdminDetails> createAdmin(@RequestBody AdminDetails adminDetails) {
		return service.getUser(id);
	   
	}
   
	@GetMapping("/admin/getAdmin/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Optional<AdminDetails> getAdmin(@PathVariable int id) {
		return service.getAdmin(id);
	   
	}
   
	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}
	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welsome to user Profile";
	}
	
	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to admin Profile";
		
	}
	

	
	
	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
						authRequest.getPassword()));
	  if (authentication.isAuthenticated()) {
		  return jwtService.generateToken(authRequest.getUsername());
	  } else {
		  throw new UsernameNotFoundException("Invalid user request !");
	  }
	}
	}
	

