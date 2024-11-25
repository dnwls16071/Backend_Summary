package com.jwj.spring.config;

import com.jwj.customLibrary.memory.MemoryController;
import com.jwj.customLibrary.memory.MemoryFinder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(name = "memory", havingValue = "on")
public class MemoryConfig {

	@Bean
	public MemoryFinder memoryFinder() {
		return new MemoryFinder();
	}

	@Bean
	public MemoryController memoryController() {
		return new MemoryController(memoryFinder());
	}
}
