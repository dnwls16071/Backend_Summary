package com.jwj.springTestCode.spring.service;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.dto.ProductRequest;
import com.jwj.springTestCode.spring.dto.ProductServiceRequest;
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

	public ProductResponse createProduct(ProductServiceRequest request) {

		// next Product Number 생성
		String nextProductNumber = createNextProductNumber();
		Product entity = request.toEntity(nextProductNumber);
		productRepository.save(entity);

		return ProductResponse.of(entity);
	}

	private String createNextProductNumber() {
		String latestProductNumber = productRepository.findLatestProduct();
		if (latestProductNumber == null) {
			return "001";
		}

		int latestProductNumberInt = Integer.valueOf(latestProductNumber);
		int nextProductNumberInt = latestProductNumberInt + 1;
		return String.format("%03d", nextProductNumberInt);
	}
}
