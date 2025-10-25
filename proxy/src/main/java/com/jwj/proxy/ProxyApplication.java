package com.jwj.proxy;

import com.jwj.proxy.advice.ProxyFactoryConfigV1;
import com.jwj.proxy.advice.ProxyFactoryConfigV2;
import com.jwj.proxy.aop_aspect.AppConfig;
import com.jwj.proxy.reflect.DynamicProxyBasicConfig;
import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.ThreadLocalLogTrace;
import com.jwj.proxy.v1_proxy.concrete_proxy.ConcreteProxyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import({InterfaceProxyConfig.class})
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
//@Import(ProxyFactoryConfigV1.class)
//@Import(ProxyFactoryConfigV2.class)
@Import(AppConfig.class)
@SpringBootApplication(scanBasePackages = "com.jwj.proxy")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
