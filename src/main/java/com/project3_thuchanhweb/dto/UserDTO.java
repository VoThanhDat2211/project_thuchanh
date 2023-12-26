package com.project3_thuchanhweb.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;

@Data
public class UserDTO {
	private int id;

	@NotBlank
	private String name;

	private String username;

	private String password;

	private String email;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthDay;
	
	private List<RoleDTO> roles;
}
