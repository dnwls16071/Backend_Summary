package com.jwj.springPrinciple.applicationcontext;

import com.jwj.springPrinciple.config.AppConfiguration;
import com.jwj.springPrinciple.discount.DiscountPolicy;
import com.jwj.springPrinciple.discount.FixDiscountPolicy;
import com.jwj.springPrinciple.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfigClass.class);

	@Test
	@DisplayName("동일한 타입의 빈이 두 개 이상 조회될 경우 중복 오류가 발생한다.")
	void findBeanByTypeDuplicate() {

		assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
	}

	@Test
	@DisplayName("동일한 타입의 빈을 모두 조회한다.")
	void findAllBeanByType() {

		Map<String, DiscountPolicy> map = ac.getBeansOfType(DiscountPolicy.class);
		for (String s : map.keySet()) {
			System.out.println("key = " + s);
			System.out.println("value = " + map.get(s));
		}
	}

	@Configuration
	static class SameBeanConfigClass {

		@Bean
		public DiscountPolicy fixDiscountPolicy() {
			return new FixDiscountPolicy();
		}

		@Bean
		public DiscountPolicy rateDiscountPolicy() {
			return new RateDiscountPolicy();
		}
	}
}
