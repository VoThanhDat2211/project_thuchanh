package com.project3_thuchanhweb.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.JwtTokenService;

@RestController
@RequestMapping("/")
public class LoginController {
	@Autowired
	JwtTokenService tokenService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	// doi tuong moi dang nhap 
	@GetMapping("/me")
	public String me (Principal p) {
		String username = p.getName();
		return username;
	}
	
	@PostMapping("/login")
	public ResponseDTO<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		return ResponseDTO.<String>builder().status(200).msg("login successful")
				.data(tokenService.createToken(username)).build();
	}
}
