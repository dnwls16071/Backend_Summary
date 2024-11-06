package com.jwj.springTestCode.spring.controller;

import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public void createOrder(OrderRequest request) {
		LocalDateTime now = LocalDateTime.now();
		orderService.createOrder(request, now);
	}
}
