package com.project3_thuchanhweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	// search by categoryName
			@Query("select p from Product p Where p.name Like :name  ")
			Page<Product> searchByName(@Param("name") String name, Pageable pageable);
			
			@Query("select p from Product p ")
			Page<Product> getAll(Pageable pageable);
}
