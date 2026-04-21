package com.ap.enotes_api_service.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ap.enotes_api_service.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	@Autowired
	private UserDetailsService UserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = "";
		String username = "";
		
		
		if(authHeader !=  null && authHeader.startsWith(authHeader)) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userByUsername = UserDetailsService.loadUserByUsername(username);
			Boolean validateToken = jwtService.validateToken(token, userByUsername);
			
//			if(validateToken) {
//				
//			}
			
		}
		
	}

}
