package com.ap.enotes_api_service.service;

import java.util.Collection;

import org.springframework.cache.Cache;

public interface CacheManagerService {

	public Collection<String> getCache();
	
	public Cache getCacheName(String cacheName);
	
	public void removeAllCache();
}
