package com.jwj.springPrinciple.service;

import com.jwj.springPrinciple.order.Order;

public interface OrderService {

	Order createOrder(Long memberId, String itemName, int itemPrice);
}
