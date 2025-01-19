package com.jwj.springPrinciple.service;

import com.jwj.springPrinciple.discount.DiscountPolicy;
import com.jwj.springPrinciple.entity.Member;
import com.jwj.springPrinciple.order.Order;
import com.jwj.springPrinciple.repository.MemberRepository;

public class OrderServiceImpl implements OrderService {

	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discount = discountPolicy.discount(member, itemPrice);
		return new Order(memberId, itemName, itemPrice, discount);
	}
}
