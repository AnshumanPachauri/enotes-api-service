package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Caching", description = "Contains all the cache management api's")
public interface CacheEndpoints {

	@GetMapping("/")
	public ResponseEntity<?> getAllCache();
	
	@GetMapping("/{cacheName}")
	public ResponseEntity<?> getCache(@PathVariable String cacheName);
	
	@DeleteMapping("/")
	public ResponseEntity<?> removeAllCache();

}
