package com.jwj.springPrinciple.applicationcontext;

import com.jwj.springPrinciple.discount.DiscountPolicy;
import com.jwj.springPrinciple.discount.FixDiscountPolicy;
import com.jwj.springPrinciple.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextExtendTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

	@Test
	@DisplayName("부모 타입으로 모두 조회한다.")
	void findAllBeanParentType() {
		Map<String, DiscountPolicy> map = ac.getBeansOfType(DiscountPolicy.class);
		assertThat(map.size()).isEqualTo(2);
	}

	@Configuration
	static class TestConfig {

		@Bean
		public DiscountPolicy rateDiscountPolicy() {
			return new RateDiscountPolicy();
		}

		@Bean
		public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
	}
}
