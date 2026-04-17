package com.ap.enotes_api_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.service.HomeService;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int id, @RequestParam String VC) throws Exception{
		
		Boolean verifyAccount = homeService.verifyAccount(id, VC);
		if(verifyAccount) {
			return CommonUtil.CreateBuildResponseMessage("Account Verified", HttpStatus.OK);
		}
		return CommonUtil.CreateErrorResponseMessage("Invalid URL.", HttpStatus.BAD_REQUEST);
	}
	
}
