package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.OrderStatus;
import com.jwj.springTestCode.spring.client.MailClient;
import com.jwj.springTestCode.spring.entity.Order;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.history.MailSendHistory;
import com.jwj.springTestCode.spring.repository.MailSendRepository;
import com.jwj.springTestCode.spring.repository.OrderProductRepository;
import com.jwj.springTestCode.spring.repository.OrderRepository;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.jwj.springTestCode.spring.ProductSellingStatus.*;
import static com.jwj.springTestCode.spring.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderStatisticsServiceTest {

	@Autowired private OrderStatisticsService orderStatisticsService;
	@Autowired private OrderProductRepository orderProductRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private MailSendRepository mailSendRepository;

	@MockBean private MailClient mailClient;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendRepository.deleteAllInBatch();
	}

	@DisplayName("매출 통계 메일을 전송한다.")
	@Test
	void sendOrderStatisticsMail() {
		// given
		LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		List<Product> products = List.of(product1, product2, product3);
		productRepository.saveAll(List.of(product1, product2, product3));

		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 4, 23, 59, 59), products);
		createPaymentCompletedOrder(now, products);
		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 5, 23, 59, 59), products);
		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 6, 0, 0), products);

		// stubbing
		when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
				.thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> histories = mailSendRepository.findAll();
		assertThat(histories).hasSize(1)
				.extracting("content")
				.containsExactlyInAnyOrder("총 매출합계는 27000원입니다.");
	}

	private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
		Order order = Order.builder()
				.products(products)
				.orderStatus(OrderStatus.PAYMENT_COMPLETED)
				.registeredDateTime(now)
				.build();
		return orderRepository.save(order);
	}
}