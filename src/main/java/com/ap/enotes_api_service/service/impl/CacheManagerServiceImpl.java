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
	public Collection<String> getCache() {
		
		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName:cacheNames) {
			@Nullable
			Cache cache = cacheManager.getCache(cacheName);
			log.info("cache Name->"+cache);
//			System.out.println();
		}
		return cacheNames;
	}

	@Override
	public Cache getCacheName(String cacheName) {
		
		Cache cache = cacheManager.getCache(cacheName);
		log.info("Cache Name = {}", cache);
		return cache;
		
	}

	@Override
	public void removeAllCache() {
		
		Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for(String cacheName:cacheNames) {
			@Nullable
			Cache cache = cacheManager.getCache(cacheName);
			log.info("cache Name->"+cache);
			cache.clear();
		}	
	}
	
}
