package com.jwj.springPrinciple.discount;

import com.jwj.springPrinciple.entity.Grade;
import com.jwj.springPrinciple.entity.Member;

public class FixDiscountPolicy implements DiscountPolicy {

	private int discountFixAmount = 1000;

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.DIAMOND) {
			return discountFixAmount;
		} else {
			return 0;
		}
	}
}
