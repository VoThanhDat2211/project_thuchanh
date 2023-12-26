package com.project3_thuchanhweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	// search by categoryName
		@Query("select c from Category c Where c.name Like :name  ")
		Page<Category> searchByName(@Param("name") String name, Pageable pageable);
}
