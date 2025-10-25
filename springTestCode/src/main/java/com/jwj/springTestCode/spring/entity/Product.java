package com.jwj.springTestCode.spring.entity;

import com.jwj.springTestCode.spring.BaseEntity;
import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productNumber;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Enumerated(EnumType.STRING)
	private ProductSellingStatus sellingStatus;

	private String name;

	private int price;

	// 외부에서 생성자를 호출할 수 없도록 접근 제어자를 private으로 설정
	private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.productNumber = productNumber;
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	// 정적 팩토리 메서드로 이름을 명확히 가지는 메서드를 작성
	public static Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		return new Product(productNumber, type, sellingStatus, name, price);
	}
}