package com.ap.enotes_api_service.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.service.JWTService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

	private String secretKey = "";
	
	/*
	 * whenever the JWTService is called, the secretKey will be automatically
	 * created as the creaation logic is written in constructor, whenever an object
	 * is made of this service class, the key will be generated.
	 */
	public JWTServiceImpl() {
		
		try {
			//this method generated the secret key based on the algorithm set in the getInstance method.
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey mySecretKey = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(mySecretKey.getEncoded());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

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
		byte[] decodedKeyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decodedKeyBytes);
	}

}
