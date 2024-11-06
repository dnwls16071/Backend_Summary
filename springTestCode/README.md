# 테스트 코드 작성 가이드

### ✅Lombok(사용 가이드)

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* @Data, @Setter, @AllArgsConstructor 사용 지양하자.
* 양방향 연관관계 시 @ToString 순환 참조 문제 발생

-----------------------
</details>

### ✅DisplayName은 섬세하게 지어라.

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* 테스트 코드 작성 시 테스트 코드의 이름을 지을 수 있는데 이 때, 사용하는 어노테이션이 바로 `@DisplayName`이다.
* 테스트 행위에 대한 결과까지 같이 기술한다.
* 도메인 용어를 사용하여 한층 추상화된 내용을 담는다.(메서드 자체 관점보다 도메인 정책 관점으로)
* 테스트 현상을 중점으로 기술하지 말 것

> ### 테스트 코드 네이밍 예시
> 
> * 예시1 : 음료를 1개 추가할 수 있다. → 음료를 1개 추가하면 주문 목록에 담긴다.
> * 예시2 : 특정 시간 이전에 주문을 생성하면 실패한다. → 영업 시작 시간 이전에는 주문을 생성할 수 없다.

-----------------------
</details>

### ✅BDD(Behavior Driven Development)

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* TDD에서 파생된 기법
* 함수 단위 테스트에 집중하기보다는 시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트한다.
* 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장한다.

-----------------------
</details>

### ✅Given - When - Then 패턴이란?

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* Given : 시나리오 진행에 필요한 모든 준비 과정(객체 값, 상태 등)
* When : 시나리오 행동 진행
* Then : 시나리오 진행에 대한 결과 명시, 검증

-----------------------
</details>

### ✅Persistence Layer 테스트

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* Persistence Layer(영속성 계층)

  * DB와의 통신을 담당하는 계층
  * Repository/DAO가 이 계층에 위치

> Persistence Layer 테스트 코드 예시

```java
@ActiveProfiles(profiles = "test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

	@Autowired private ProductRepository productRepository;

	@DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
	@Test
	void 원하는_판매상태를_가진_상품들을_조회한다() {
		// given
		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

		// then
		assertThat(products).hasSize(2)
				.extracting("productNumber", "name", "sellingStatus")
				.containsExactlyInAnyOrder(
						tuple("001", "아메리카노", SELLING),
						tuple("002", "카페라떼", HOLD)
				);
	}
}
```

> Persistence Layer 테스트 코드 설명
> * `@DataJpaTest` 어노테이션의 경우 기본적으로 내장된 인메모리 데이터베이스(ex. H2) 즉, EmbeddedDatabase를 사용하도록 설정된다.
> * 하지만 이 스프링 프로젝트에서 MySQL 데이터베이스를 사용하고 있어 `@DataJpaTest` 어노테이션을 사용하면 에러가 발생한다.
> * JPA 관련 테스트를 수행할 때 `@DataJpaTest` 어노테이션을 사용해서 진행하면 필요한 Configuration만 주입받아서 빠르게 테스트를 진행할 수 있고 롤백도 되어 간단하게 결과를 확인할 수 있다는 장점이 있다.
> * 실제 사용 중인 데이터베이스로 테스트를 수행하려면 AutoConfigurationTestDatabase를 덮어써서 내장된 인메모리 데이터베이스 대신 실제 데이터베이스로 테스트를 진행할 수 있도록 해야 한다.
>
> Persistence Layer
> * Data Access의 역할
> * 비즈니스 가공 로직이 포함되어선 안 된다. Data에 대한 CRUD에만 집중해야한다.

-----------------------
</details>