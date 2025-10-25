package com.jwj.springTestCode.spring.dto;

import com.jwj.springTestCode.spring.service.OrderService;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

	@NotEmpty(message = "상품 번호는 적어도 1개 이상이어야 한다.")
	private List<String> productNumbers;

	@Builder
	private OrderRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}

	public OrderServiceRequest toServiceRequest() {
		return OrderServiceRequest.builder()
				.productNumbers(productNumbers)
				.build();
	}
}