package com.jwj.spring_principle2.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

	private final OrderRepositoryV0 orderRepository;

	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
