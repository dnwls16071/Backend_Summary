package com.jwj.springTestCode.spring.controller;

import com.jwj.springTestCode.spring.dto.ProductServiceRequest;
import com.jwj.springTestCode.spring.service.ApiResponse;
import com.jwj.springTestCode.spring.dto.ProductRequest;
import com.jwj.springTestCode.spring.dto.ProductResponse;
import com.jwj.springTestCode.spring.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductApiController {

	private final ProductService productService;

	@GetMapping("/api/v1/products/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts() {
		return ApiResponse.ok(productService.getSellingProducts());
	}

	@PostMapping("/api/v1/products/new")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
		return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
	}
}
