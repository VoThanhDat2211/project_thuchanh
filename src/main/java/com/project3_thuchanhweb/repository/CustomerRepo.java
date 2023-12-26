package com.project3_thuchanhweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Bill;
import com.project3_thuchanhweb.entity.User;

@Repository
public interface CustomerRepo extends JpaRepository<User, Integer>{
	
	@Query("SELECT b FROM Bill b WHERE b.user.username = :username")
	List<Bill> searchBillByUser(@Param("username") String username);
}
