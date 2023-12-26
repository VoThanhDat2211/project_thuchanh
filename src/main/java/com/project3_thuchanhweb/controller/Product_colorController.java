package com.project3_thuchanhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.Product_colorDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.Product_colorService;

@RestController
@RequestMapping("/admin/product_color")
public class Product_colorController {
	
	@Autowired
	Product_colorService pcSe;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody Product_colorDTO pcDTO) {
		pcSe.create(pcDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		pcSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}

	@PatchMapping("/")
	public ResponseDTO<Void> update(@RequestBody Product_colorDTO pcDTO) {
		pcSe.update(pcDTO);

		return ResponseDTO.<Void>builder().status(200).msg("update successful").build();
	}
}
