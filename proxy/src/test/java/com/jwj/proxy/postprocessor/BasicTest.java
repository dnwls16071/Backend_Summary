package com.jwj.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BasicTest {

	@Test
	void basicConfig() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(BasicConfig.class);

		A a = ac.getBean("beanA", A.class);
		a.helloA();;

		assertThatThrownBy(() -> ac.getBean(B.class));
	}

	@Configuration
	static class BasicConfig {

		// A만 스프링 빈으로 등록
		@Bean(name = "beanA")
		public A a() {
			return new A();
		}
	}

	@Slf4j
	static class A {
		public void helloA() {
			log.info("hello A");
		}
	}

	@Slf4j
	static class B {
		public void helloB() {
			log.info("hello B");
		}
	}
}
