package com.project3_thuchanhweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.User;
import java.util.List;
import com.project3_thuchanhweb.entity.Role;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("select u from User u Where u.name Like :name  ")
	Page<User> seaarchByName(@Param("name") String name, Pageable pageable);
	
	User findByUsername(String username);


}
