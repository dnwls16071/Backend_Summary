package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.dto.OrderResponse;
import com.jwj.springTestCode.spring.entity.Order;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.repository.OrderRepository;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public OrderResponse createOrder(OrderRequest request, LocalDateTime registererDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		Order order = Order.createOrder(products, registererDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}
}
