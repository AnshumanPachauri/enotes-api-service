package com.ap.enotes_api_service.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.utils.CommonUtil;

public class AuditAwareConfig implements AuditorAware<Integer>{

	
	/*
	 * This method returns the user that's logged in....so wherever @CreatedBy
	 * or @UpdatedBy annotations are used, it returns the info of loggedin user to
	 * the data with these annotations. and automatically sets the user info to those entity columns.
	 */	
	@Override
	public Optional<Integer> getCurrentAuditor() {
		
		//We will get the currently logged In User.
		User loggedInUser = CommonUtil.getLoggedInUser();
		
//		return Optional.of(2);
		return Optional.of(loggedInUser.getId());
		
	}

}
