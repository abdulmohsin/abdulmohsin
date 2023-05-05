package com.sita.producer;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class ProducerApplication {


	private static final String FLIGHT_CACHE = "flightCache";
	
	@Autowired
	private CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@GetMapping("/get")
	public Object getPairValue(@RequestParam String key) {

		Cache flightCache = cacheManager.getCache(FLIGHT_CACHE);
		if(!Objects.isNull(flightCache.get(key))) {
			return flightCache.get(key).get();
		}
		return "No value exists for key : "+ key;
	}
	
	@GetMapping("/put")
	public String putValue(@RequestParam String key, @RequestParam String value) {

		Cache flightCache = cacheManager.getCache(FLIGHT_CACHE);
		if(!Objects.isNull(flightCache.get(key))) {
			return "Key-value pair already exists for key : "+ key;
		}
		else {
			flightCache.put(key, value);
			return "Key-value pair inserted successfully";
		}
	}

}
