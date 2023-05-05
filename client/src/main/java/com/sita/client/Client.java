package com.sita.client;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class Client {


	private static final String FLIGHT_CACHE = "flightCache";
	
	@Autowired
	private CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

	@GetMapping("/get")
	public Object getPairValue(@RequestParam(required = false) String key) {

		Cache flightCache = cacheManager.getCache(FLIGHT_CACHE);
		if(!Objects.isNull(flightCache.get(key)) && !ObjectUtils.isEmpty(key)) {
			return flightCache.get(key).get();
		}
	
		return "No value exists for key : "+ key;
	}
	
}
