package com.jwj.springPrinciple.applicationcontext;

import com.jwj.springPrinciple.config.AppConfiguration;
import com.jwj.springPrinciple.discount.DiscountPolicy;
import com.jwj.springPrinciple.service.MemberService;
import com.jwj.springPrinciple.service.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextInfoTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);
	//ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);

	@Test
	@DisplayName("모든 빈을 조회한다.")
	void findAllBean() {

		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			Object bean = ac.getBean(beanDefinitionName);
			System.out.println("name = " + beanDefinitionName + ", object = " + bean);
		}
	}

	// Get the role hint for this BeanDefinition. The role hint provides the frameworks as well as tools an indication of the role and importance of a particular BeanDefinition.
	// See Also:
	// ROLE_APPLICATION, ROLE_SUPPORT, ROLE_INFRASTRUCTURE
	@Test
	@DisplayName("빈을 선택적으로 조회한다.")
	void findApplicationBean() {

		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

			if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
				Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + ", object = " + bean);
			}
		}
	}

	@Test
	@DisplayName("특정 타입의 빈을 조회한다. - 1")
	void findBeanByName() {

		Object bean = ac.getBean("discountPolicy");
		assertThat(bean).isInstanceOf(DiscountPolicy.class);
	}

	@Test
	@DisplayName("특정 타입의 빈을 조회한다. - 2")
	void findBeanByType() {

		MemberService memberService = ac.getBean("memberService", MemberService.class);
		assertThat(memberService).isInstanceOf(MemberService.class);
	}

	@Test
	@DisplayName("빈을 찾을 수 없다면 예외가 발생한다.")
	void findBeanNotFound() {

		assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("XXXXX", MemberService.class));
	}
}
