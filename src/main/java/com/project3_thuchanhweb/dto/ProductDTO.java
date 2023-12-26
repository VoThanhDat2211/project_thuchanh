package com.project3_thuchanhweb.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProductDTO {
	private int id;

	private String name;

	private double price;

	private String description;
	
	private String image;
	
	@JsonIgnore
	private MultipartFile file;

	private CategoryDTO category;
}


