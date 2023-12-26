package com.project3_thuchanhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Role;
import java.util.List;


@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role  findByName(String name);;
}
