package com.jwj.springTestCode.spring.dto;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.ProductType;
import com.jwj.springTestCode.spring.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequest {

	@NotNull(message = "상품 타입은 필수입니다.")
	private ProductType type;

	@NotNull(message = "상품 판매상태는 필수입니다.")
	private ProductSellingStatus sellingStatus;

	@NotBlank(message = "상품 이름은 필수입니다.")
	private String name;

	@Positive(message = "상품 가격은 양수여야 합니다.")
	private int price;

	@Builder
	private ProductRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public Product toEntity(String nextProductNumber) {
		return Product.createProduct(nextProductNumber, type, sellingStatus, name, price);
	}

	public ProductServiceRequest toServiceRequest() {
		return ProductServiceRequest.builder()
				.type(type)
				.sellingStatus(sellingStatus)
				.name(name)
				.price(price)
				.build();
	}
}
