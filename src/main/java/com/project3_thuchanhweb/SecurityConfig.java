package com.project3_thuchanhweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	// authenticate
	@Autowired // dependences inject (DI)
	public void config(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.userDetailsService(userDetailsService) // kiem tra username cos trong db hay khong
				.passwordEncoder(new BCryptPasswordEncoder()); // doi chieu password dung de dang nhap voi db
	}

	// authorize
	@Bean
	SecurityFilterChain config(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN") // duong dan
																			// admin co quyen
																			// ROLE_ADMIN
				.antMatchers("/member/**","/customer/**").authenticated()
				.anyRequest().permitAll()// duong dan member chi can dang nhap // duong dam con lai moi quyen deu co the truy cap
				.and().csrf(csrf -> csrf.disable());
		
		http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
