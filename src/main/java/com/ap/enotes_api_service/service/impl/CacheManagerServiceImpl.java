package com.ap.enotes_api_service.service.impl;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.service.CacheManagerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CacheManagerServiceImpl implements CacheManagerService{

	@Autowired
	private CacheManager cacheManager;
	
	@Override
	public void getCache() {
		
		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName:cacheNames) {
			@Nullable
			Cache cache = cacheManager.getCache(cacheName);
			log.info("cache Name->"+cacheName);
//			System.out.println();
		}
		
	}
	
}
