package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.ProductType;
import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.dto.OrderResponse;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.entity.Stock;
import com.jwj.springTestCode.spring.repository.OrderProductRepository;
import com.jwj.springTestCode.spring.repository.OrderRepository;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import com.jwj.springTestCode.spring.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.jwj.springTestCode.spring.ProductSellingStatus.*;
import static com.jwj.springTestCode.spring.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

	@Autowired private ProductRepository productRepository;
	@Autowired private OrderService orderService;
	@Autowired private OrderProductRepository orderProductRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired
	private StockRepository stockRepository;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
	}

	@DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
	@Test
	void 주문번호_리스트를_받아_주문을_생성한다() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();			// 시간과 같이 외부 요소의 경우 별도로 분리

		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of("001", "002"))
				.build();

		// when
		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
				.extracting("registeredDateTime", "totalPrice")
				.contains(registeredDateTime, 8500);
		assertThat(response.getProducts()).hasSize(2)
				.extracting("productNumber", "price")
				.containsExactlyInAnyOrder(
						tuple("001", 4000),
						tuple("002", 4500)
				);
	}

	@DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
	@Test
	void 중복되는_상품번호_리스트로_주문을_생성할_수_있다() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();			// 시간과 같이 외부 요소의 경우 별도로 분리

		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, SELLING, "카페라떼", 4000);
		productRepository.saveAll(List.of(product1, product2));

		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of("001", "001"))
				.build();

		// when
		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		// then
		assertThat(response.getProducts()).hasSize(2);
	}

	@DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
	@Test
	void 재고와_관련된_상품이_포함되어_있는_주문번호_리스트를_받아_주문을_생성한다() {
		// given
		LocalDateTime now = LocalDateTime.now();

		Product product1 = createProduct("001", BOTTLE, SELLING, "망고 주스", 4000);
		Product product2 = createProduct("002", BAKERY, SELLING, "빵", 4000);
		Product product3 = createProduct("003", HANDMADE, SELLING, "아메리카노", 4000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock stock1 = Stock.createStock("001", 2);
		Stock stock2 = Stock.createStock("002", 2);
		stockRepository.saveAll(List.of(stock1, stock2));

		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of("001", "001", "002", "003"))
				.build();

		// when
		OrderResponse response = orderService.createOrder(request, now);

		// then
		assertThat(response.getId()).isNotNull();
		assertThat(response)
				.extracting("registeredDateTime", "totalPrice")
				.contains(now, 16000);
		assertThat(response.getProducts()).hasSize(4)
				.extracting("productNumber", "price")
				.containsExactlyInAnyOrder(
						tuple("001", 4000),
						tuple("001", 4000),
						tuple("002", 4000),
						tuple("003", 4000)
				);
		List<Stock> all = stockRepository.findAll();
		assertThat(all).hasSize(2)
				.extracting("productNumber", "quantity")
				.containsExactlyInAnyOrder(
						tuple("001", 2),
						tuple("002", 2)
				);
	}

	@DisplayName("재고가 부족한 상품으로 주문을 생성하는 경우 예외가 발생한다.")
	@Test
	void 재고가_부족한_상품으로_주문을_생성하는_경우_예외가_발생한다() {
		// given
		LocalDateTime now = LocalDateTime.now();

		Product product1 = createProduct("001", BOTTLE, SELLING, "망고 주스", 4000);
		Product product2 = createProduct("002", BAKERY, SELLING, "빵", 4000);
		Product product3 = createProduct("003", HANDMADE, SELLING, "아메리카노", 4000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock stock1 = Stock.createStock("001", 1);
		Stock stock2 = Stock.createStock("002", 2);
		stockRepository.saveAll(List.of(stock1, stock2));

		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of("001", "001", "002", "003"))
				.build();

		// when & then
		assertThatThrownBy(() -> orderService.createOrder(request, now))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("재고가 부족한 상품이 있습니다.");
	}

	private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		return Product.createProduct(productNumber, type, sellingStatus, name, price);
	}
}