package com.ap.enotes_api_service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ap.enotes_api_service.entity.User;

public class CustomUserDetails implements UserDetails{
	
	private User user;
	
	/*
	 * This method will consume the user whose email exists in the DB. The userName
	 * is checked in CustomUserSetailsServiceImpl for the username in DB, then it is
	 * returned in the form of a custmoUserDetail. using the parameterised constructor of this methohd.
	 */
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	/*
	 * This method gets all the roles given to the user in request header and adds
	 * them in the SimpleGrantAuthority object to grant the roles to the user.
	 */
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> simpleGrantedAuthority = new ArrayList<>();
		user.getRoles().forEach(role -> {
			simpleGrantedAuthority.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return null;
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
