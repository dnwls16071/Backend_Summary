package com.jwj.spring_principle2.v4;

import com.jwj.spring_principle2.template.AbstractTemplate;
import com.jwj.spring_principle2.util.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

	private final OrderServiceV4 orderService;
	private final LogTrace trace;

	@GetMapping("/v4/request")
	public String request(String itemId) {

		AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
			@Override
			protected String call() {
				orderService.orderItem(itemId);
				return "ok";
			}
		};
		return template.execute("OrderController.request()");
	}
}
