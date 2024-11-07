package com.jwj.springTestCode.spring.repository;

import static com.jwj.springTestCode.spring.ProductSellingStatus.*;
import static com.jwj.springTestCode.spring.ProductType.*;
import com.jwj.springTestCode.spring.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

	@Autowired private ProductRepository productRepository;

	@DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
	@Test
	void 원하는_판매상태를_가진_상품들을_조회한다() {
		// given
		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

		// then
		assertThat(products).hasSize(2)
				.extracting("productNumber", "name", "sellingStatus")
				.containsExactlyInAnyOrder(
						tuple("001", "아메리카노", SELLING),
						tuple("002", "카페라떼", HOLD)
				);
	}

	@DisplayName("상품번호 리스트로 상품들을 조회한다.")
	@Test
	void findAllByProductNumberIn() {
		// given
		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

		// then
		assertThat(products).hasSize(2)
				.extracting("productNumber", "name", "sellingStatus")
				.containsExactlyInAnyOrder(
						tuple("001", "아메리카노", SELLING),
						tuple("002", "카페라떼", HOLD)
				);
	}
}