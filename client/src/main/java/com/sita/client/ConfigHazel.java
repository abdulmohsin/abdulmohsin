package com.sita.client;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
public class ConfigHazel {
	private static final String FLIGHT_CACHE = "flightCache";
	
	@Value("${hazelCastPort:5701}")
	private int hazelCastPort;

	@Bean
	public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
		return new HazelcastCacheManager(hazelcastInstance);
	}

	@Bean
	public HazelcastInstance hazelcastInstance(Config config) {
		return Hazelcast.newHazelcastInstance(config);
	}

	@Bean
	public Config config() {
		Config config = new Config();

		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		config.getNetworkConfig().setPort(hazelCastPort);
		// disable multicast config for demo
		joinConfig.getMulticastConfig().setEnabled(false);

		// enable tcp/ip config for demo
		joinConfig.getTcpIpConfig().setMembers(Collections.singletonList("localhost")).setEnabled(true);

		MapConfig usersMapConfig = new MapConfig().setName(FLIGHT_CACHE).setTimeToLiveSeconds(600)
				.setEvictionPolicy(EvictionPolicy.LFU);

		config.addMapConfig(usersMapConfig);

		return config;
	}

}
