package com.jwj.springPrinciple.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

	@Test
	@DisplayName("싱글톤 패턴을 적용한 객체를 사용한다.")
	void singleton() {
		SingletonService instance1 = SingletonService.getInstance();
		SingletonService instance2 = SingletonService.getInstance();

		assertThat(instance1).isSameAs(instance2);
	}
}
