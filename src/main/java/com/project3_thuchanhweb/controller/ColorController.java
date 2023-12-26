package com.project3_thuchanhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.ColorDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.ColorService;

@RestController
@RequestMapping("/color")
public class ColorController {
	@Autowired
	ColorService colorSe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody ColorDTO colorDTO) {
		colorSe.create(colorDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		colorSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}


	@GetMapping("/")
	public ResponseDTO<ColorDTO> getById(@RequestParam int id) {
		ColorDTO colorDTO = colorSe.getbyId(id);

		return ResponseDTO.<ColorDTO>builder().status(200).data(colorDTO).build();
	}
}
