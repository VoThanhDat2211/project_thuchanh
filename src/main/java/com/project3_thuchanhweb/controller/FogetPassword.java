package com.project3_thuchanhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.UserService;

@RestController
@RequestMapping("/fogetPassword")
public class FogetPassword {
	@Autowired
	UserService userSe;
	@PostMapping("/")
	public ResponseDTO<Void> fogetPassword(@RequestParam("username") String username) {
		userSe.newPassword(username);
		return ResponseDTO.<Void>builder().status(200).msg("Create newPassword successful!").build();
	}
}
