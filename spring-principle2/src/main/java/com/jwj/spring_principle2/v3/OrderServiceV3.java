package com.jwj.spring_principle2.v3;

import com.jwj.spring_principle2.util.LogTrace;
import com.jwj.spring_principle2.util.TraceStatus;
import com.jwj.spring_principle2.v1.OrderRepositoryV1;
import com.jwj.spring_principle2.v2.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

	private final OrderRepositoryV3 orderRepository;
	private final LogTrace trace;

	public void orderItem(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderService.orderItem()");
			orderRepository.save(itemId);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
