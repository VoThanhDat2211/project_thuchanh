package com.project3_thuchanhweb.dto;

import lombok.Data;

@Data
public class BillStatisticDTO {
	private long quantity;

	private int month;

	private int year;

	public BillStatisticDTO(long quantity, int month, int year) {
		this.month = month;
		this.quantity = quantity;
		this.year = year;
	}
}
