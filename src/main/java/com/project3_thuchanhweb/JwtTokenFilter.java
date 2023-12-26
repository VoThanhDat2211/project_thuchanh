package com.project3_thuchanhweb;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project3_thuchanhweb.service.JwtTokenService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	JwtTokenService jwtTokenSe;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// lấy token từ header có thuộc tính "authorization"
		String bearer = request.getHeader("Authorization");

		// kiem tra token co ton tai va dung dinh dang hay khong
		if (bearer != null && bearer.startsWith("Bearer ")) {
			String token = bearer.substring(7);

			String username = jwtTokenSe.getUsername(token);
			// kiem tra username co ton tai hay khong
			if (username != null) {
				// tao doi tuong UserDetails chua cac thong tin can thiet cua nguoi dang nhap
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// tao doi tuong dai dien cho doi tuong xac thuc chua phan anh quyen han truy
				// cap
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, "",
						userDetails.getAuthorities());
				// luu tru thong tin xac thuc vao security context
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}
}
