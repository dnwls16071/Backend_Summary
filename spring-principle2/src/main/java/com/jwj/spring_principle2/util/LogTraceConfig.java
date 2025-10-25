package com.jwj.spring_principle2.util;

import com.jwj.spring_principle2.threadlocal.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

	@Bean
	public LogTrace logTrace() {
		//return new FieldLogTrace();
		return new ThreadLocalLogTrace();
	}
}
