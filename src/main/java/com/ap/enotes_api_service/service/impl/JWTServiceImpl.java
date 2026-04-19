package com.ap.enotes_api_service.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.service.JWTService;

import io.jsonwebtoken.Jwts;

@Service
public class JWTServiceImpl implements JWTService {

	@Override
	public String generateToken(User user) {
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("roles", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		
		String jwtToken = Jwts.builder()
		.claims().add(claims)
		.subject(user.getEmail())
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+60*60*10))
		.and()
		.signWith(getKey())
		.compact();
		
		return jwtToken;
	}

	private Key getKey() {
			
		return null;
	}

}
