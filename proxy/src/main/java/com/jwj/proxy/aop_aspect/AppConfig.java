package com.jwj.proxy.aop_aspect;

import com.jwj.proxy.config.AppV1Config;
import com.jwj.proxy.config.AppV2Config;
import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import({AppV1Config.class, AppV2Config.class})
public class AppConfig {

	@Bean
	public LogTraceAspect logTraceAspect(LogTrace logTrace) {
		return new LogTraceAspect(logTrace);
	}
}
