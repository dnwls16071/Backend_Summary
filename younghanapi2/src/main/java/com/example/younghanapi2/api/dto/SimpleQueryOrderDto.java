package com.example.younghanapi2.api.dto;

import com.example.younghanapi2.domain.Address;
import com.example.younghanapi2.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleQueryOrderDto {

	private Long orderId;
	private String name;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;

	// JPA에서 DTO를 바로 조회할 때는 필드로다가 선언
	public SimpleQueryOrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}
