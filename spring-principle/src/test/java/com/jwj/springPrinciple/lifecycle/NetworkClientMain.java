package com.jwj.springPrinciple.lifecycle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class NetworkClientMain {

	@Test
	@DisplayName("초기화, 소멸 메서드 지정")
	void lifecycleTest() {

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(NetworkClientConfig.class);
		NetworkClient networkClient = ac.getBean("networkClient", NetworkClient.class);
		ac.close();
	}

	@Configuration
	static class NetworkClientConfig {

		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://localhost:8080");
			return networkClient;
		}
	}
}
