package com.ap.enotes_api_service.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

	/*
	 * Secret key will be created evrytime the application restarts, as at the time
	 * of restart the service will be autowired and the onstructor will be called.
	 */
	
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
		
		/*
		 * Token will be generated everytime the user tries to login, the secret key
		 * will remain same until the application is restarted, but the token will be
		 * changed on every login.
		 */
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("id", user.getId());
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

	@Override
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String token) {
		Claims claimsPayload = Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
		return claimsPayload;
	}

	private SecretKey decryptKey(String secretKey) {
		byte[] decodeKeyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decodeKeyBytes);
	}

	@Override
	public Boolean validateToken(String token, UserDetails customUserDetails) {
		String userName = extractUsername(token);
		Boolean isTokenExpired = isExtractedTokenExpired(token);
		
		if(userName.equalsIgnoreCase(customUserDetails.getUsername()) && !isTokenExpired) return true;
		return false;
	}
	private Boolean isExtractedTokenExpired(String token) {
		Claims userClaims = extractAllClaims(token);
		return userClaims.getExpiration().before(new Date());
	}

}
