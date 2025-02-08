package com.jwj.proxy.config;

import com.jwj.proxy.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AppV1Config {

	//@Bean
	public OrderControllerV1 orderControllerV1() {
		return new OrderControllerImplV1(orderServiceV1());
	}

	//@Bean
	public OrderServiceV1 orderServiceV1() {
		return new OrderServiceImplV1(orderRepositoryV1());
	}

	//@Bean
	public OrderRepositoryV1 orderRepositoryV1() {
		return new OrderRepositoryImplV1();
	}
}
