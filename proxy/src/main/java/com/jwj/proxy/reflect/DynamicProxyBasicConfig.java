package com.jwj.proxy.reflect;

import com.jwj.proxy.v1.*;
import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

//@Configuration
public class DynamicProxyBasicConfig {

	//@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		OrderControllerV1 orderController = new OrderControllerImplV1(orderServiceV1(logTrace));
		OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
				new Class[]{OrderControllerV1.class},
				new LogTraceBasicHandler(orderController, logTrace)
		);
		return proxy;
	}

	//@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		OrderServiceV1 orderService = new OrderServiceImplV1(orderRepositoryV1(logTrace));
		OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
				new Class[]{OrderServiceV1.class},
				new LogTraceBasicHandler(orderService, logTrace)
		);
		return proxy;
	}

	//@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1 orderRepository = new OrderRepositoryImplV1();
		OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
				new Class[]{OrderRepositoryV1.class},
				new LogTraceBasicHandler(orderRepository, logTrace)
		);
		return proxy;
	}

	//@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}
}
