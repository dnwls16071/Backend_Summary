package com.example.younghanapi1.repository;

import com.example.younghanapi1.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

	private String memberName;			// 회원명 검색
	private OrderStatus orderStatus;	// 주문 상태 검색
}
