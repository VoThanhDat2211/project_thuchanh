package com.project3_thuchanhweb.dto;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class BillItemsDTO {
	private int id;
	
	//@JsonBackReference
	@JsonIgnoreProperties("billItems")
	private BillDTO bill;
	
	private ProductDTO product;
	
	private Product_colorDTO product_colorDTO;
	
	private long buyPrice;
	
	@Min(0)
	private int quantity;
	
	
}
