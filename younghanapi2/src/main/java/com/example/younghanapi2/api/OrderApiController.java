package com.example.younghanapi2.api;

import com.example.younghanapi2.api.dto.OrderFlatDto;
import com.example.younghanapi2.api.dto.OrderQueryDto;
import com.example.younghanapi2.domain.Address;
import com.example.younghanapi2.domain.Order;
import com.example.younghanapi2.domain.OrderItem;
import com.example.younghanapi2.domain.OrderStatus;
import com.example.younghanapi2.repository.OrderRepository;
import com.example.younghanapi2.repository.OrderSearch;
import com.example.younghanapi2.repository.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;

	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {

			order.getMember().getName();
			order.getDelivery().getAddress();
			List<OrderItem> orderItems = order.getOrderItems();

			for (OrderItem orderItem : orderItems) {

				orderItem.getItem().getName();
			}
		}
		return all;
	}

	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		List<OrderDto> list = all.stream().map(OrderDto::new).toList();
		return list;
	}

	@GetMapping("/api/v3/orders")
	public List<OrderDto> ordersV3() {
		List<Order> all = orderRepository.findAllWithItem();
		List<OrderDto> list = all.stream().map(OrderDto::new).toList();
		return list;
	}

	@GetMapping("/api/v3.1/orders")
	public List<OrderDto> ordersV3_page(
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "100") int limit
	) {
		List<Order> all = orderRepository.findAllWithMemberDelivery(offset, limit);
		List<OrderDto> list = all.stream().map(OrderDto::new).toList();
		return list;
	}

	@GetMapping("/api/v4/orders")
	public List<OrderQueryDto> ordersV4() {
		return orderQueryRepository.findOrderItemQueryDtos();
	}

	@GetMapping("/api/v5/orders")
	public List<OrderQueryDto> ordersV5() {
		return orderQueryRepository.findAllByDto_optimization();
	}

	@GetMapping("/api/v6/orders")
	public List<OrderFlatDto> ordersV6() {
		return orderQueryRepository.findAllByDto_flat();
	}

	@Data
	static class OrderDto {

		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		// private List<OrderItem> orderItems;(엔티티 X, DTO O)
		private List<OrderItemDto> orderItems;

		public OrderDto(Order order) {
			this.orderId = order.getId();
			this.name = order.getMember().getName();
			this.orderDate = order.getOrderDate();
			this.orderStatus = order.getStatus();
			this.address = order.getDelivery().getAddress();
			this.orderItems = order.getOrderItems().stream()
					.map(OrderItemDto::new)
					.collect(Collectors.toList());
		}
	}

	@Data
	static class OrderItemDto {

		private String itemName;
		private int orderPrice;
		private int count;

		public OrderItemDto(OrderItem orderItem) {
			this.itemName = orderItem.getItem().getName();
			this.orderPrice = orderItem.getOrderPrice();
			this.count = orderItem.getCount();
		}
	}
}
