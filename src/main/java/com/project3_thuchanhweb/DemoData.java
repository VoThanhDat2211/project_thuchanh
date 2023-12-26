package com.project3_thuchanhweb;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project3_thuchanhweb.entity.Role;
import com.project3_thuchanhweb.entity.User;
import com.project3_thuchanhweb.repository.RoleRepo;
import com.project3_thuchanhweb.repository.UserRepo;

// insert data demo into data 
public class DemoData implements ApplicationRunner{

	@Autowired
	RoleRepo roleRe;
	@Autowired
	UserRepo userRe;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// insert role 
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		
		if(roleRe.findByName(role.getName()) == null) {
			try {
				User user = new User();
				user.setName("SYS_ADMIN");
				user.setUsername("sysadmin");
				user.setPassword(new BCryptPasswordEncoder().encode("123456"));
				user.setBirthday(new Date());
				user.setEmail("sysadmin@gmail.com");
				user.setRoles(Arrays.asList(role));
			
				userRe.save(user);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
