package com.project3_thuchanhweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.CategoryDTO;
import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.service.CategoryService;

@RestController
@RequestMapping("/admin/category")

public class CategoryController {
	@Autowired
	CategoryService categorySe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody CategoryDTO categotyDTO) {
		categorySe.create(categotyDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		categorySe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}

	@PatchMapping("/")
	public ResponseDTO<Void> update(@RequestBody CategoryDTO categoryDTO) {
		categorySe.updateName(categoryDTO);

		return ResponseDTO.<Void>builder().status(200).msg("update successful").build();
	}

	@GetMapping("/")
	public ResponseDTO<CategoryDTO> getById(@RequestParam int id) {
		CategoryDTO categoryDTO = categorySe.getbyId(id);

		return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryDTO).build();
	}

	@GetMapping("/search-by-categoryName")
	public ResponseDTO<PageDTO<List<CategoryDTO>>> searchByCategoryName(@RequestBody SearchDTO searchDTO) {
		PageDTO<List<CategoryDTO>> categoryDTOs = categorySe.searchByName(searchDTO);
		return ResponseDTO.<PageDTO<List<CategoryDTO>>>builder().status(200).data(categoryDTOs).build();
	}
}
