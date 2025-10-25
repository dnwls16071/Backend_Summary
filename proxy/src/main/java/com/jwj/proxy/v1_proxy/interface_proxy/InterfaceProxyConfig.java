package com.jwj.proxy.v1_proxy.interface_proxy;

import com.jwj.proxy.v1.*;
import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class InterfaceProxyConfig {

	// @Bean
	public OrderControllerV1 orderController(LogTrace logTrace) {
		OrderControllerImplV1 controllerImpl = new OrderControllerImplV1(orderService(logTrace));
		return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
	}

	// @Bean
	public OrderServiceV1 orderService(LogTrace logTrace) {
		OrderServiceImplV1 serviceImpl = new OrderServiceImplV1(orderRepository(logTrace));
		return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
	}

	// @Bean
	public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
		OrderRepositoryImplV1 repositoryImpl = new OrderRepositoryImplV1();
		return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
	}

	// @Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
