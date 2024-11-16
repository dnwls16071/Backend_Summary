package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.dto.ProductRequest;
import com.jwj.springTestCode.spring.dto.ProductResponse;
import com.jwj.springTestCode.spring.dto.ProductServiceRequest;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jwj.springTestCode.spring.ProductSellingStatus.*;
import static com.jwj.springTestCode.spring.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductServiceTest {

	@Autowired private ProductService productService;
	@Autowired private ProductRepository productRepository;

	@BeforeEach
	void beforeEach() {

	}

	@DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1만큼 증가한 값이어야 한다.")
	@Test
	void createProduct() {
		// given
		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();
		ProductServiceRequest serviceRequest = request.toServiceRequest();

		// when
		ProductResponse productResponse = productService.createProduct(serviceRequest);

		// then
		assertThat(productResponse)
				.extracting("productNumber", "type")
				.contains("004", request.getType());

		List<Product> productList = productRepository.findAll();
		assertThat(productList).hasSize(4);
	}

	@DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
	@Test
	void createProductWhenProductsIsEmpty() {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();
		ProductServiceRequest serviceRequest = request.toServiceRequest();

		// when
		ProductResponse productResponse = productService.createProduct(serviceRequest);

		// then
		assertThat(productResponse)
				.extracting("productNumber", "type")
				.contains("001", request.getType());
	}
}