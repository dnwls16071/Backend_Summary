package com.jwj.spring_principle2.v4;

import com.jwj.spring_principle2.template.AbstractTemplate;
import com.jwj.spring_principle2.util.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

	private final OrderRepositoryV4 orderRepository;
	private final LogTrace trace;

	public void orderItem(String itemId) {
		AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
			@Override
			protected Void call() {
				orderRepository.save(itemId);
				return null;
			}
		};
		template.execute("OrderService.orderItem()");
	}
}
