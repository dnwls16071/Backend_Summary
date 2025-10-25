package com.jwj.proxy.v1_proxy.concrete_proxy;

import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.TraceStatus;
import com.jwj.proxy.v2.OrderServiceV2;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

	private final OrderServiceV2 target;
	private final LogTrace logTrace;

	public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
		super(null);	// 기본 생성자 없음
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public void orderItem(String itemId) {
		TraceStatus status = null;
		try {
			status = logTrace.begin("OrderService.orderItem()");
			//target 호출
			target.orderItem(itemId);
			logTrace.end(status);
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
