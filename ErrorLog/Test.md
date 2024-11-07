### ✅JDBC exception executing SQL [select p.productNumber from Product p order by p.id desc limit 1] [Unknown column 'p.productNumber' in 'field list'] [n/a]; SQL [n/a] org.springframework.dao.InvalidDataAccessResourceUsageException: JDBC exception executing SQL [select p.productNumber from Product p order by p.id desc limit 1] [Unknown column 'p.productNumber' in 'field list'] [n/a]; SQL [n/a]

<details>
   <summary> 에러 내용 해결 방법 (👈 Click)</summary>
<br />

* 가장 최근에 등록된 상품 번호를 찾는 쿼리 메서드를 Native Query문을 활용해 작성하는 과정에서 제목과 같은 오류가 발생했다.
* 오류 내용을 보면 알 수 없는 필드명이라는 것을 볼 수 있는데 처음엔 이게 왜 안되는 것인지 고민했다.
* Product 엔티티 코드를 보면 다음과 같다.

```java
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
```

* 분명 엔티티에 명시된 필드명을 보면 productNumber라는 필드명이 버젓이 존재하는데 왜 알 수 없는 필드로 인식되는지 이해가 안 갔다.
* JPA는 자바의 camelCase 필드명을 데이터베이스의 snake_case 컬럼명으로 자동 변환한다.
* 따라서 Java 코드의 productNumber은 데이터베이스에서 product_number 컬럼명으로 저장되기 때문에 기존 자바의 코드를 네이티브 쿼리에 작성하게 되면 제목과 같은 오류가 발생하게 되는 것이다.

-----------------------
</details>