package com.jwj.spring_principle2.v5;

import com.jwj.spring_principle2.callback.TraceTemplate;
import com.jwj.spring_principle2.template.AbstractTemplate;
import com.jwj.spring_principle2.util.LogTrace;
import com.jwj.spring_principle2.v4.OrderRepositoryV4;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

	private final OrderRepositoryV5 orderRepository;
	private final TraceTemplate template;

	public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
		this.orderRepository = orderRepository;
		this.template = new TraceTemplate(trace);
	}

	public void orderItem(String itemId) {
		template.execute("OrderService.orderItem()", () -> {
			orderRepository.save(itemId);
			return null;
		});
	}
}
