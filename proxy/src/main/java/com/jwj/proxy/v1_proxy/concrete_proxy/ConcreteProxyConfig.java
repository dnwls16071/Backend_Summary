package com.jwj.proxy.v1_proxy.concrete_proxy;

import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v2.OrderControllerV2;
import com.jwj.proxy.v2.OrderRepositoryV2;
import com.jwj.proxy.v2.OrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ConcreteProxyConfig {

	//@Bean
	public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
		OrderControllerV2 controllerImpl = new OrderControllerV2(orderServiceV2(logTrace));
		return new OrderControllerConcreteProxy(controllerImpl, logTrace);
	}

	//@Bean
	public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
		OrderServiceV2 serviceImpl = new OrderServiceV2(orderRepositoryV2(logTrace));
		return new OrderServiceConcreteProxy(serviceImpl, logTrace);
	}

	//@Bean
	public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
		OrderRepositoryV2 repositoryImpl = new OrderRepositoryV2();
		return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
	}
}
