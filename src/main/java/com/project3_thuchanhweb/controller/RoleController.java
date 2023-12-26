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
import com.project3_thuchanhweb.dto.RoleDTO;
import com.project3_thuchanhweb.service.RoleService;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	RoleService roleSe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody RoleDTO roleDTO) {
		roleSe.create(roleDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		roleSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}

	@PatchMapping("/")
	public ResponseDTO<Void> update(@RequestBody RoleDTO roleDTO) {
		roleSe.updateName(roleDTO);

		return ResponseDTO.<Void>builder().status(200).msg("update successful").build();
	}

	@GetMapping("/")
	public ResponseDTO<RoleDTO> getById(@RequestParam int id) {
		RoleDTO roleDTO = roleSe.getbyId(id);

		return ResponseDTO.<RoleDTO>builder().status(200).data(roleDTO).build();
	}

	
}