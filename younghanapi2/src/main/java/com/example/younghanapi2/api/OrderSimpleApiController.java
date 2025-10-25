package com.example.younghanapi2.api;

import com.example.younghanapi2.api.dto.SimpleQueryOrderDto;
import com.example.younghanapi2.domain.Address;
import com.example.younghanapi2.domain.Order;
import com.example.younghanapi2.domain.OrderStatus;
import com.example.younghanapi2.repository.OrderRepository;
import com.example.younghanapi2.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;

	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		return all;
	}

	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		List<SimpleOrderDto> list = all.stream().map(SimpleOrderDto::new).toList();
		return list;
	}

	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> all = orderRepository.findAllWithMemberDelivery();
		List<SimpleOrderDto> list = all.stream().map(SimpleOrderDto::new).toList();
		return list;
	}

	@GetMapping("/api/v4/simple-orders")
	public List<SimpleQueryOrderDto> ordersV4() {
		List<SimpleQueryOrderDto> all = orderRepository.findOrderDtos();
		return all;
	}

	@Data
	static class SimpleOrderDto {

		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Order order) {
			this.orderId = order.getId();
			this.name = order.getMember().getName();
			this.orderDate = order.getOrderDate();
			this.orderStatus = order.getStatus();
			this.address = order.getDelivery().getAddress();
		}
	}
}
