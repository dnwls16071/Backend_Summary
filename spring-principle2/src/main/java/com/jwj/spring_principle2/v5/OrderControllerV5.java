package com.jwj.spring_principle2.v5;

import com.jwj.spring_principle2.callback.TraceCallback;
import com.jwj.spring_principle2.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV5 {

	private final OrderServiceV5 orderService;
	private final TraceTemplate template;

	@GetMapping("/v5/request")
	public String request(String itemId) {
		return template.execute("OrderController.request()", new
				TraceCallback<>() {
					@Override
					public String call() {
						orderService.orderItem(itemId);
						return "ok";
					}
				});
	}
}
