package com.jwj.springTestCode.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jwj.springTestCode.spring.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

	@DisplayName("상품 타입이 재고 관련 타입(BOTTLE, BAKERY)인지를 체크한다.")
	@Test
	void 상품_타입이_재고_관련_타입인지를_체크한다() {
		// given
		ProductType givenType = HANDMADE;

		// when
		boolean result = ProductType.containsStockType(givenType);

		// then
		assertThat(result).isFalse();
	}
}