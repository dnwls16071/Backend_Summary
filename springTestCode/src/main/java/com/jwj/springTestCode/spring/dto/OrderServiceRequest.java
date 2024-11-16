package com.jwj.springTestCode.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// Order 도메인에서 컨트롤러 DTO가 서비스 계층으로 그대로 전달되면 의존관계가 형성됨
// 이를 개선하기 위해 DTO 내부에서 서비스 계층 전용 DTO로 변환하는 부분을 고려

@Getter
@NoArgsConstructor
public class OrderServiceRequest {

	private List<String> productNumbers;

	@Builder
	private OrderServiceRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}
}