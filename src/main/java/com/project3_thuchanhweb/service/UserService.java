package com.project3_thuchanhweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.UserDTO;
import com.project3_thuchanhweb.entity.Role;
import com.project3_thuchanhweb.entity.User;
import com.project3_thuchanhweb.repository.RoleRepo;
import com.project3_thuchanhweb.repository.UserRepo;

public interface UserService {
	void create(UserDTO userDTO);

	void updateName(UserDTO userDTO);

	void delete(int id);

	UserDTO getbyId(int id);

	String newPassword(String username);
}

@Service
class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepo userRe;
	@Autowired
	RoleRepo roleRe;
	@Autowired
	EmailService emailSe;
	
	@Override
	@Transactional
	public void create(UserDTO userDTO) {

		User u = new ModelMapper().map(userDTO, User.class);

		u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));

		userRe.save(u);

	}

	@Transactional
	@Override
	public void updateName(UserDTO userDTO) {
		User u = userRe.findById(userDTO.getId()).orElse(null);
		if (u != null) {
			u.setName(userDTO.getName());
			userRe.save(u);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		User u = userRe.findById(id).orElse(null);
		if (u != null) {
			userRe.deleteById(id);
		}

	}

	@Override
	public UserDTO getbyId(int id) {
		User u = userRe.findById(id).orElse(null);

		if (u != null) {
			UserDTO userDTO = convert(u);
			return userDTO;
		}
		return null;
	}

	private UserDTO convert(User u) {
		return new ModelMapper().map(u, UserDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// tim usernam trong db
		User userEntity = userRe.findByUsername(username);

		// chuyen tu entity -> securiry
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		// chuyen vai tro -> quyen
		for (Role role : userEntity.getRoles()) {

			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		System.out.println(authorities);
		return new org.springframework.security.core.userdetails.User(username, userEntity.getPassword(), authorities);
	}

	@Override
	public String newPassword(String username) {
		User user = userRe.findByUsername(username);
		if (user != null) {
			// update mat khau da encode vao db 
			String newPassword = randomPassword();
			user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
			userRe.save(user);
			
			// gui mat khau moi ve email nguoi dung
			emailSe.sendNewPassword(user.getEmail(), newPassword);
		}

		return null;
	}

	private String randomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int lenght = 6;
		Random rd = new Random();
		StringBuilder newPassword = new StringBuilder(6);

		for (int i = 0; i < lenght; i++) {
			int randomIndex = rd.nextInt(characters.length());
			char randomChar = characters.charAt(randomIndex);

			newPassword.append(randomChar);
		}

		return newPassword.toString();
	}

}
