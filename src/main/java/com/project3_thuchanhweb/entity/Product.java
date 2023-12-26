package com.project3_thuchanhweb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private double price;

	private String description;

	private String image;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Product_color> productColors;

	@ManyToOne
	private Category category;

}