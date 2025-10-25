package com.jwj.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BeanPostProcessorTest {

	@Test
	void postProcessor() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

		//Bean named 'beanA' is expected to be of type 'com.jwj.proxy.postprocessor.BeanPostProcessorTest$A' but was actually of type 'com.jwj.proxy.postprocessor.BeanPostProcessorTest$B'
		//A a = ac.getBean("beanA", A.class);
		B b = ac.getBean("beanA", B.class);
		b.helloB();

		assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(A.class));
	}


	@Configuration
	static class BeanPostProcessorConfig {

		@Bean(name = "beanA")
		public A a() {
			return new A();
		}

		@Bean
		public AToBPostProcessor helloPostProcessor() {
			return new AToBPostProcessor();
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

	// A타입의 빈을 B타입으로 바꿔치기하는 빈 후처리기 등록
	@Slf4j
	static class AToBPostProcessor implements BeanPostProcessor {

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			log.info("beanName={} bean={}", beanName, bean);
			if (bean instanceof A) {
				return new B();
			}
			return bean;
		}
	}
}