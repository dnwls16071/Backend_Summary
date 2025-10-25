package com.jwj.springTestCode.spring.controller;

import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.dto.OrderResponse;
import com.jwj.springTestCode.spring.service.ApiResponse;
import com.jwj.springTestCode.spring.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
		LocalDateTime now = LocalDateTime.now();
		OrderResponse response = orderService.createOrder(request.toServiceRequest(), now);
		return ApiResponse.ok(response);
	}
}
