package com.project3_thuchanhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.dto.UserDTO;
import com.project3_thuchanhweb.service.UserService;

@RestController
@RequestMapping("/admin/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody UserDTO userDTO) {
		// System.out.println(userDTO.getRoles());
		userService.create(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		userService.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}

	@PatchMapping("/")
	public ResponseDTO<Void> update(@RequestBody UserDTO userDTO) {
		userService.updateName(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("update successful").build();
	}

	@GetMapping("/")
	public ResponseDTO<UserDTO> getById(@RequestParam int id) {
		UserDTO userDTO = userService.getbyId(id);

		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
	}

	

}
