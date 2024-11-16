package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.ProductType;
import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.dto.OrderResponse;
import com.jwj.springTestCode.spring.dto.OrderServiceRequest;
import com.jwj.springTestCode.spring.entity.Order;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.entity.Stock;
import com.jwj.springTestCode.spring.repository.OrderRepository;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import com.jwj.springTestCode.spring.repository.StockRepository;
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
	private final StockRepository stockRepository;

	public OrderResponse createOrder(OrderServiceRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		// 재고 감소 로직 추가
		List<String> list = products.stream()
				.filter(product -> ProductType.containsStockType(product.getType()))
				.map(product -> product.getProductNumber())
				.toList();

		// 재고 엔티티 조회
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(list);
		Map<String, Stock> stockMap = stocks.stream()
				.collect(Collectors.toMap(Stock::getProductNumber, s -> s));

		// 상품별 카운팅
		Map<Stock, Long> productCountingMap = stocks.stream()
				.collect(Collectors.groupingBy(product -> product, Collectors.counting()));

		// 재고 차감 시도
		for (String number : list) {
			Stock stock = stockMap.get(number);
			int quantity = productCountingMap.get(stock).intValue();
			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);
		}

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