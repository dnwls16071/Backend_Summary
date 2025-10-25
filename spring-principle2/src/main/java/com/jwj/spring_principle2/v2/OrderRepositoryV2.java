package com.jwj.spring_principle2.v2;

import com.jwj.spring_principle2.util.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

	private final HelloTraceV2 trace;

	public void save(String itemId) {
		TraceStatus status = null;
		try {
			status = trace.begin("OrderRepository.save()");
			//저장 로직
			if (itemId.equals("ex")) {
				throw new IllegalStateException("예외 발생!");
			}
			sleep(1000);
			trace.end(status);
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
