package com.ap.enotes_api_service.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	/*
	 * This method will be used at the time of authentication to check if the user
	 * exists in the database or not.
	 * 
	 * In case the user exists, this method will return a customUserDetails object with user as a parameter.
	 * 
	 * Therefore this method will return the user in the form of customUserDetails in case its email exists.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Invalid Email :- " + username);
		}
		
		return new CustomUserDetails(user);
	}

}
