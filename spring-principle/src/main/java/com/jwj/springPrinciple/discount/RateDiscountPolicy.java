package com.jwj.springPrinciple.discount;

import com.jwj.springPrinciple.entity.Grade;
import com.jwj.springPrinciple.entity.Member;

public class RateDiscountPolicy implements DiscountPolicy {

	private int discountPolicy = 10;

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.DIAMOND) {
			return price * discountPolicy / 100;
		} else {
			return 0;
		}
	}
}
