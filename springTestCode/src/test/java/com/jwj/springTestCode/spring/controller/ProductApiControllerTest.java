package com.jwj.springTestCode.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwj.springTestCode.spring.dto.ProductRequest;
import com.jwj.springTestCode.spring.dto.ProductResponse;
import com.jwj.springTestCode.spring.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.jwj.springTestCode.spring.ProductSellingStatus.SELLING;
import static com.jwj.springTestCode.spring.ProductType.HANDMADE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductApiController.class)
class ProductApiControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private ProductService productService;

	@DisplayName("신규 상품을 등록한다.")
	@Test
	void createProduct() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post(
						"/api/v1/products/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON));
	}

	@DisplayName("신규 상품 등록 시 상품 타입은 필수여야 한다.")
	@Test
	void createProductWithoutType() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
				"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 판매상태는 필수여야 한다.")
	@Test
	void createProductWithoutSellingStatus() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.name("아메리카노")
				.price(4000)
				.build();


		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 이름은 필수여야 한다.")
	@Test
	void createProductWithoutName() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.sellingStatus(SELLING)
				.type(HANDMADE)
				.price(4000)
				.build();


		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 가격은 양수여야 한다.")
	@Test
	void createProductWithZeroPrice() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(-8000)
				.build();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("판매 상품을 조회한다.")
	@Test
	void getSellingProducts() throws Exception {
		// given
		List<ProductResponse> result = List.of();
		when(productService.getSellingProducts()).thenReturn(result);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/products/selling")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code").value("200"))
		.andExpect(jsonPath("$.message").value("OK"))
		.andExpect(jsonPath("$.httpStatus").value("OK"))
		.andExpect(jsonPath("$.data").isArray());
	}
}