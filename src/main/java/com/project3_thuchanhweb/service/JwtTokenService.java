package com.project3_thuchanhweb.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

@Data
@Service
public class JwtTokenService {
	@Value("${jwt.secret}")
	private String secretkey;

	private int validity;

	public String createToken(String username) {
		// tạo claims subject - thông tin của chủ sở hữu
		Claims claims = Jwts.claims().setSubject(username);

		Date now = new Date(); // thời gian được tạo
		Date exp = new Date(now.getTime() + 5 * 1000 * 60); // thời gian hoạt động

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}

	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getUsername(String token) {
		try {
			return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
