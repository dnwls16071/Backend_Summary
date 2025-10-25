package com.jwj.spring.selector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

class HelloImportSelectorTest {

	@Test
	@DisplayName("정적인 방식")
	void staticConfig() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(StaticConfig.class);
		HelloBean bean = annotationConfigApplicationContext.getBean(HelloBean.class);
		assertThat(bean).isNotNull();
	}

	@Test
	@DisplayName("동적인 방식")
	void dynamicConfig() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(DynamicConfig.class);
		DynamicConfig bean = annotationConfigApplicationContext.getBean(DynamicConfig.class);
		assertThat(bean).isNotNull();
	}

	@Configuration
	@Import(HelloConfig.class)
	static class StaticConfig {

	}

	@Configuration
	@Import(HelloImportSelector.class)	// ImportSelector 인터페이스 구현체 클래스
	static class DynamicConfig {

	}
}