package com.jwj.springTestCode.spring.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

	@DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
	@Test
	void 재고보다_많은_수의_수량으로_차감_시도하는_경우_예외가_발생한다() {
		// given
		Stock stock = Stock.createStock("001", 5);

		// when & then
		assertThatThrownBy(() -> stock.deductQuantity(10))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("차감할 재고 수량이 없습니다.");
	}

	@DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
	@Test
	void 재고를_주어진_개수만큼_차감할_수_있다() {
		// given
		Stock stock = Stock.createStock("001", 5);

		// when
		stock.deductQuantity(2);

		// then
		assertThat(stock.getQuantity()).isEqualTo(3);
	}
}