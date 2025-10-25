package com.jwj.proxy.v1;

public class OrderServiceImplV1 implements OrderServiceV1 {

	private final OrderRepositoryV1 orderRepository;

	public OrderServiceImplV1(OrderRepositoryV1 orderRepository) {
		this.orderRepository = orderRepository;
	}
	@Override
	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
