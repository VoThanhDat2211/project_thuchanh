package com.project3_thuchanhweb.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BillDTO {
	private int id;
	
	
	private Date buyDate;
	
	private UserDTO user;
	
	//@JsonManagedReference
	private List<BillItemsDTO> billItems;
}

