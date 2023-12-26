package com.project3_thuchanhweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.LastModified;

import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.ProductDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.service.ProductService;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
	@Value("${upload.folder}")
	private String uploadFolder;

	@Autowired
	ProductService productSe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@ModelAttribute ProductDTO productDTO) throws IllegalStateException, IOException {
		if (!productDTO.getFile().isEmpty()) {
			if (!new File(uploadFolder).exists()) {
				new File(uploadFolder).mkdirs();
			}

			// lay ten file upload
			String filename = productDTO.getFile().getOriginalFilename();
			//
			String extension = filename.substring(filename.lastIndexOf("."));
			// luu vao o D

			String newFileName = UUID.randomUUID() + extension;
			File file = new File(uploadFolder + newFileName);
			
			productDTO.getFile().transferTo(file);
			// luu ten anh xuong database
			productDTO.setImage(newFileName);
		}

		productSe.create(productDTO);
		
		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		productSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}

	@PatchMapping("/")
	public ResponseDTO<Void> update(@RequestBody ProductDTO productDTO) {
		productSe.updateName(productDTO);

		return ResponseDTO.<Void>builder().status(200).msg("update successful").build();
	}

	@GetMapping("/")
	public ResponseDTO<ProductDTO> getById(@RequestParam int id) {
		ProductDTO productDTO = productSe.getbyId(id);

		return ResponseDTO.<ProductDTO>builder().status(200).data(productDTO).build();
	}

	@GetMapping("/search-by-productName")
	public ResponseDTO<PageDTO<List<ProductDTO>>> searchByProductName(@RequestBody SearchDTO searchDTO) {
		PageDTO<List<ProductDTO>> productDTOs = productSe.searchByName(searchDTO);
		return ResponseDTO.<PageDTO<List<ProductDTO>>>builder().status(200).data(productDTOs).build();
	}
}
