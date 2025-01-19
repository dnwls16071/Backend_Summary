package com.jwj.springPrinciple.discount;

import com.jwj.springPrinciple.entity.Member;
import org.springframework.stereotype.Component;

@Component
public interface DiscountPolicy {

	int discount(Member member, int price);
}
