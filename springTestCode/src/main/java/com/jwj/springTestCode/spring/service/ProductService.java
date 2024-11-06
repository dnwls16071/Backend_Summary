package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.entity.Product;
import com.jwj.springTestCode.spring.dto.ProductResponse;
import com.jwj.springTestCode.spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
				.map(product -> ProductResponse.of(product))
				.collect(Collectors.toList());
	}
}
