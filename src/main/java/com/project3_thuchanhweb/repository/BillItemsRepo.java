package com.project3_thuchanhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.BillItems;

@Repository
public interface BillItemsRepo extends JpaRepository<BillItems, Integer> {
	

}
