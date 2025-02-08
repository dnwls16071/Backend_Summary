package com.jwj.spring_principle2.v4;

import com.jwj.spring_principle2.template.AbstractTemplate;
import com.jwj.spring_principle2.util.LogTrace;
import com.jwj.spring_principle2.util.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

	private final LogTrace trace;

	public void save(String itemId) {
		AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
			@Override
			protected Void call() {
				//저장 로직
				if (itemId.equals("ex")) {
					throw new IllegalStateException("예외 발생!");
				}
				sleep(1000);
				return null;
			}
		};
		template.execute("OrderRepository.save()");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
