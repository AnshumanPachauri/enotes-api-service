package com.ap.enotes_api_service.config.security;

import java.io.IOException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ap.enotes_api_service.handler.GenericResponse;
import com.ap.enotes_api_service.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	@Autowired
	private UserDetailsService UserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		
		if(authHeader !=  null && authHeader.startsWith(authHeader)) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userByUsername = UserDetailsService.loadUserByUsername(username);
			Boolean validateToken = jwtService.validateToken(token, userByUsername);
			
			if(validateToken) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userByUsername, null, userByUsername.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
		}
		}catch (Exception e) {
//			e.printStackTrace();
			response.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			Object errorBody = GenericResponse.builder()
			.message(e.getMessage())
			.status("Failed")
			.responseStatus(HttpStatus.UNAUTHORIZED).build().createResponse().getBody();
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorBody));
			return; 
		}
		filterChain.doFilter(request, response);
		
	}

}
