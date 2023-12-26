package com.project3_thuchanhweb.dto;

import lombok.Data;

@Data
public class PageDTO<T> {
	private int totalPages;

	private long totalElements;

	private T data;
}
