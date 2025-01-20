package com.jwj.springPrinciple.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class StatefulServiceTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonConfig.class);

	@Test
	@DisplayName("싱글톤은 상태를 보존하는 설계를 하면 장애가 발생한다.")
	void singletonStatefulService() {

		StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
		StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

		int userA = statefulService1.order("userA", 1000);
		int userB = statefulService2.order("userB", 2000);

		assertThat(userA).isEqualTo(1000);
	}

	@Configuration
	static class SingletonConfig {

		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}
}
