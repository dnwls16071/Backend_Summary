package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.OrderStatus;
import com.jwj.springTestCode.spring.entity.Order;
import com.jwj.springTestCode.spring.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

	private final OrderRepository orderRepository;
	private final MailService mailService;

	public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {

		// 해당일자에 결제된 주문들을 조회한다.
		List<Order> orders = orderRepository.findOrdersByDateTime(orderDate.atStartOfDay(), orderDate.plusDays(1).atStartOfDay(), OrderStatus.PAYMENT_COMPLETED);

		// 총 매출 합계
		int total = orders.stream()
				.mapToInt(order -> order.getTotalPrice())
				.sum();

		// 메일 전송
		boolean b = mailService.sendEmail("no-reply@cafekiosk.com", email, String.format("[매출통계] %s", orderDate), String.format("총 매출합계는 %s원입니다.", total));

		if (!b) {
			throw new RuntimeException("메일 전송에 실패했습니다.");
		}
		return b;
	}
}
