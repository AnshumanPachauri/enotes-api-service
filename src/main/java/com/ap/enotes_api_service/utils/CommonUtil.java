package com.ap.enotes_api_service.utils;

import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ap.enotes_api_service.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static ResponseEntity<?> CreateBuildResponse(Object data, HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("Success")
				.message("Success")
				.data(data)
				.build();
		
		return response.createResponse();
		
	}
	
	public static ResponseEntity<?> CreateBuildResponseMessage(String message, HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("Success")
				.message(message)
				.build();
		
		return response.createResponse();
		
	}
	
	public static ResponseEntity<?> CreateErrorResponse(Object data, HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		
		return response.createResponse();
		
	}
	
	public static ResponseEntity<?> CreateErrorResponseMessage(String message, HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();
		
		return response.createResponse();
		
	}

	
	public static String getContentType(String originalFileName) {
		
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch(extension) {
		case "pdf":
			return "applicaction/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "txt":
			return "text/plan";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}
	}

	public static String getRequestUrl(HttpServletRequest request) {
		
		String serverUrl = request.getRequestURL().toString();
		serverUrl = serverUrl.replace(request.getServletPath(), "");
		return serverUrl;
	}
	
	
	
}
