package com.ap.enotes_api_service.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.UserResponseDto;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponseDto mapedLoggedInUserResponse = modelMapper.map(loggedInUser, UserResponseDto.class);
		return CommonUtil.CreateBuildResponse(mapedLoggedInUserResponse, HttpStatus.OK);
	}
	
}
