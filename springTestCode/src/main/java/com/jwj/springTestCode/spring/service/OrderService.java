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
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public OrderResponse createOrder(OrderRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		Order order = Order.createOrder(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
				.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		return productNumbers.stream()
				.map(productMap::get)
				.collect(Collectors.toList());
	}

}