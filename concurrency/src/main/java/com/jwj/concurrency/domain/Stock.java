package com.jwj.concurrency.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;

	private Long quantity;

	// 도메인 측 재고 감소 로직
	public void decrease(Long productId, Long quantity) {
		if (this.quantity - quantity < 0) {
			throw new RuntimeException("재고가 부족합니다.");
		}
		this.quantity -= quantity;
	}

	public Stock(Long productId, Long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
