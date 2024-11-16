package com.jwj.springTestCode.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwj.springTestCode.spring.dto.OrderRequest;
import com.jwj.springTestCode.spring.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderApiController.class)
class OrderApiControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private OrderService orderService;

	@DisplayName("신규 주문을 등록한다.")
	@Test
	void createOrder() throws Exception {
		// given
		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of("001"))
				.build();

		// when & then
		mockMvc.perform(post("/api/v1/orders/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(APPLICATION_JSON)
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200"))
				.andExpect(jsonPath("$.message").value("OK"))
				.andExpect(jsonPath("$.httpStatus").value("OK"));
	}

	@DisplayName("신규 주문을 등록할 때 상품 번호는 1개 이상이어야 한다.")
	@Test
	void createOrderWithEmptyProductNumbers() throws Exception {
		// given
		OrderRequest request = OrderRequest.builder()
				.productNumbers(List.of())
				.build();

		// when & then
		mockMvc.perform(post("/api/v1/orders/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 번호는 적어도 1개 이상이어야 한다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}
}