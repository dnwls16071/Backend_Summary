package com.jwj.springTestCode.spring.entity;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.ProductType;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.jwj.springTestCode.spring.OrderStatus.INIT;
import static com.jwj.springTestCode.spring.ProductSellingStatus.*;
import static com.jwj.springTestCode.spring.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderTest {

	@Autowired private ProductRepository productRepository;

	@DisplayName("주문 생성 시 상품 리스트에서 주문의 촘 금액을 계산한다.")
	@Test
	void 주문_생성_시_상품_리스트에서_주문의_총_금액을_계산한다() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();			// 시간과 같이 외부 요소의 경우 별도로 분리

		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		List<Product> products = productRepository.saveAll(List.of(product1, product2, product3));

		// when
		Order order = Order.createOrder(products, registeredDateTime);

		// then
		assertThat(order.getTotalPrice()).isEqualTo(13500);
	}

	@DisplayName("주문 생성 시 주문 초기 상태는 INIT이다.")
	@Test
	void 주문_생성_시_주문_초기_상태는_INIT이다() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();			// 시간과 같이 외부 요소의 경우 별도로 분리

		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);

		List<Product> products = productRepository.saveAll(List.of(product1, product2));

		// when
		Order order = Order.createOrder(products, registeredDateTime);

		// then
		assertThat(order.getOrderStatus()).isEqualByComparingTo(INIT);
	}

	@DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
	@Test
	void 주문_생성_시_주문_등록_시간을_기록한다() {
		// given
		LocalDateTime registeredDateTime = LocalDateTime.now();			// 시간과 같이 외부 요소의 경우 별도로 분리

		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);

		List<Product> products = productRepository.saveAll(List.of(product1, product2));

		// when
		Order order = Order.createOrder(products, registeredDateTime);

		// then
		assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
	}

	private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		return Product.createProduct(productNumber, type, sellingStatus, name, price);
	}
}