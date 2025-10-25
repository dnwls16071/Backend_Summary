package com.jwj.springPrinciple.config;

import com.jwj.springPrinciple.discount.DiscountPolicy;
import com.jwj.springPrinciple.discount.FixDiscountPolicy;
import com.jwj.springPrinciple.repository.MemberRepository;
import com.jwj.springPrinciple.repository.MemberRepositoryImpl;
import com.jwj.springPrinciple.service.MemberService;
import com.jwj.springPrinciple.service.MemberServiceImpl;
import com.jwj.springPrinciple.service.OrderService;
import com.jwj.springPrinciple.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	@Bean
	public MemberRepository memberRepository() {
		return new MemberRepositoryImpl();
	}

	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}

	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}

	@Bean
	public DiscountPolicy discountPolicy() {
		return new FixDiscountPolicy();
		// return new RateDiscountPolicy();
	}
}
