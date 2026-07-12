package com.ap.enotes_api_service.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.endpoints.CacheEndpoints;
import com.ap.enotes_api_service.service.CacheManagerService;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/cache")
public class CacheController implements CacheEndpoints {

    private final AuthController authController;

	@Autowired
	private CacheManagerService cacheManagerService;

    CacheController(AuthController authController) {
        this.authController = authController;
    }
	
	@Override
	public ResponseEntity<?> getAllCache() {
		Collection<String> cache = cacheManagerService.getCache();
		return CommonUtil.CreateBuildResponse(cache, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCache(String cacheName) {
		Cache cacheName2 = cacheManagerService.getCacheName(cacheName);
		return CommonUtil.CreateBuildResponse(cacheName2, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeAllCache() {
		cacheManagerService.removeAllCache();
		return CommonUtil.CreateBuildResponse("Cache Deleted", HttpStatus.OK);
	}

}
