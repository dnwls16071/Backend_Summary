package com.jwj.springTestCode.spring.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderRequest {
	private List<String> productNumbers;
}