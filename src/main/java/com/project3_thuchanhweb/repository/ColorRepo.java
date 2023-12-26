package com.project3_thuchanhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project3_thuchanhweb.entity.Color;

@Repository
public interface ColorRepo extends JpaRepository<Color, Integer> {

}
