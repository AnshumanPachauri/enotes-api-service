package com.ap.enotes_api_service.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.UserDto;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.repository.RoleRepository;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.Validation;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Validation validation; 
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean register(UserDto userDto) {
		
		validation.userValidation(userDto);
		
		User mappedUser = modelMapper.map(userDto, User.class);
		
		User savedUser = userRepository.save(mappedUser);
		
		if(ObjectUtils.isEmpty(savedUser)) {
			return false;
		}
		
		return true;
	}

}
