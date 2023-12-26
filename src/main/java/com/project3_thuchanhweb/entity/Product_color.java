package com.project3_thuchanhweb.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.JoinColumn;

import lombok.Data;

@Data
@Entity
public class Product_color {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Color color;
	
	@ManyToOne
	private Product product;
	
	private int quantity;
	
	// 1 sản phẩm có nhiều ảnh
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="product_image",joinColumns  = @JoinColumn(name="product_color_id"))
	@Column(name="image")
	private List<String> images;
}
