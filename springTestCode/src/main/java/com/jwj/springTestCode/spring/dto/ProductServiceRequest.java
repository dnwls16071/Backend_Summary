package com.jwj.springTestCode.spring.dto;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.ProductType;
import com.jwj.springTestCode.spring.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductServiceRequest {

	private ProductType type;
	private ProductSellingStatus sellingStatus;
	private String name;
	private int price;

	@Builder
	private ProductServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public Product toEntity(String nextProductNumber) {
		return Product.createProduct(nextProductNumber, type, sellingStatus, name, price);
	}
}
