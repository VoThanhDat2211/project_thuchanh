package com.project3_thuchanhweb.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Product_colorDTO {
	private int id;
	
	private int quantity;
	
	private ColorDTO color;
	
	private ProductDTO product;
	
	@JsonIgnore
	private List<MultipartFile> files ;
}
