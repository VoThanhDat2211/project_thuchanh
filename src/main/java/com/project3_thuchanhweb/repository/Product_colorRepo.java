package com.project3_thuchanhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Product_color;

@Repository
public interface Product_colorRepo extends JpaRepository<Product_color, Integer> {

}
