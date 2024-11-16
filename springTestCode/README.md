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

### ✅@Transactional 어노테이션

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* `@Transactional` 어노테이션은 트랜잭션을 관리하는 어노테이션이다.
* `@Transactional` 어노테이션에는 옵션이 존재한다.
  * readOnly = true
    * 읽기 전용 트랜잭션
    * CRUD에서 CUD가 동작X, R만 동작O
    * JPA에서 읽기 전용 트랜잭션으로 설정하게 되면 스냅샷 저장, 변경 감지 X → 약간의 성능 향상
    * CQRS(Command and Query Responsibility Segregation) : 데이터 저장소로부터의 읽기와 업데이트 작업을 분리하는 패턴을 잘 지킬 수 있다.

⭐⭐ 추천 코드 스타일 : 클래스 내에 있는 모든 행위에 대해서 readOnly = true로 가져가되 CUD 작업의 경우에는 @Transactional을 별도로 기재

-----------------------
</details>

### ✅Presentation Layer 테스트

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

```java
@WebMvcTest(ProductApiController.class)
class ProductApiControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private ProductService productService;

	@DisplayName("신규 상품을 등록한다.")
	@Test
	void createProduct() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post(
						"/api/v1/products/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON));
	}

	@DisplayName("신규 상품 등록 시 상품 타입은 필수여야 한다.")
	@Test
	void createProductWithoutType() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(4000)
				.build();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
				"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 판매상태는 필수여야 한다.")
	@Test
	void createProductWithoutSellingStatus() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.name("아메리카노")
				.price(4000)
				.build();


		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 이름은 필수여야 한다.")
	@Test
	void createProductWithoutName() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.sellingStatus(SELLING)
				.type(HANDMADE)
				.price(4000)
				.build();


		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("신규 상품 등록 시 상품 가격은 양수여야 한다.")
	@Test
	void createProductWithZeroPrice() throws Exception {
		// given
		ProductRequest request = ProductRequest.builder()
				.type(HANDMADE)
				.sellingStatus(SELLING)
				.name("아메리카노")
				.price(-8000)
				.build();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(
								"/api/v1/products/new")
						.content(objectMapper.writeValueAsString(request))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
				.andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@DisplayName("판매 상품을 조회한다.")
	@Test
	void getSellingProducts() throws Exception {
		// given
		List<ProductResponse> result = List.of();
		when(productService.getSellingProducts()).thenReturn(result);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/products/selling")
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code").value("200"))
		.andExpect(jsonPath("$.message").value("OK"))
		.andExpect(jsonPath("$.httpStatus").value("OK"))
		.andExpect(jsonPath("$.data").isArray());
	}
}
```

* Presentation Layer 테스트의 경우 외부 세계의 요청을 가장 먼저 받는 계층이다.
* 파라미터에 대한 최소한의 검증을 수행한다.

-----------------------
</details>
 
### ✅Controller 계층에서 사용되는 DTO와 Service 계층에서 사용되는 DTO를 구분해야하는 이유

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

> 관심사의 분리(Separation of Concerns)
* Controller DTO : API 요청/응답에 특화된 데이터 구조를 가지고 있고 이 데이터의 유효성 검증을 위한 용도로 사용된다.
* Service DTO : 비즈니스 로직 처리에 필요한 데이터 구조

> 계층 간 결합도 감소
* 각 계층의 DTO가 분리되어 있으면 한 계층의 변경이 다른 계층에 영향을 미치지 않게 된다.
* API 스펙이 변경되어도 Service 계층의 코드를 수정할 필요가 없다.

-----------------------
</details>

### ✅Mock

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

```java
@SpringBootTest
class OrderStatisticsServiceTest {

	@Autowired private OrderStatisticsService orderStatisticsService;
	@Autowired private OrderProductRepository orderProductRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private MailSendRepository mailSendRepository;

	@MockBean private MailClient mailClient;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendRepository.deleteAllInBatch();
	}

	@DisplayName("매출 통계 메일을 전송한다.")
	@Test
	void sendOrderStatisticsMail() {
		// given
		LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

		Product product1 = Product.createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = Product.createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
		Product product3 = Product.createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 5000);

		List<Product> products = List.of(product1, product2, product3);
		productRepository.saveAll(List.of(product1, product2, product3));

		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 4, 23, 59, 59), products);
		createPaymentCompletedOrder(now, products);
		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 5, 23, 59, 59), products);
		createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 6, 0, 0), products);

		// stubbing
		when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
				.thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> histories = mailSendRepository.findAll();
		assertThat(histories).hasSize(1)
				.extracting("content")
				.containsExactlyInAnyOrder("총 매출합계는 27000원입니다.");
	}

	private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
		Order order = Order.builder()
				.products(products)
				.orderStatus(OrderStatus.PAYMENT_COMPLETED)
				.registeredDateTime(now)
				.build();
		return orderRepository.save(order);
	}
}
```

* 메일 전송과 같은 외부 네트워크를 타는 긴 작업의 경우 실제로 트랜잭션에 참여하지 않아도 되기 때문에 이런 서비스의 경우 트랜잭션을 걸지 않는게 좋다.
* 외부 시스템 API 호출 시(Ex. 결제 API 등) 트랜잭션의 제어가 불가능하기 때문에 외부 네트워크를 필요로 하는 서비스의 경우 트랜잭션을 걸지 않는 것이 좋다.
* 백엔드에서 처리할 수 없는 외부 시스템의 트랜잭션을 포함하면 안되며 각 시스템의 트랜잭션 경계를 명확히 구분해야 한다.

-----------------------
</details>

### ✅Test Double

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

* 용어 정리
  * Dummy : 아무것도 하지 않는 깡통 객체
  * Fake : 단순한 형태로 동일한 기능은 수행하나, 프로덕션에는 쓰기 부족한 객체(Ex. FakeRepository)
  * Stub : 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체, 그 외에는 응답하지 않는다.
  * Spy : Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체, 일부는 실제 객체처럼 동작시키고 일부만 Stubbing 할 수 있다.
  * Mock : 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

-----------------------
</details>

### ✅@Mock, @Spy, @InjectMocks

<details>
   <summary> 정리한 내용 보기 (👈 Click)</summary>
<br />

```java
class MailServiceTest {

    @Mock private MailClient mailClient;			    // @Mock : 가짜 객체 생성
    @Mock private MailSendRepository mailSendRepository; 	    // @Mock : 가짜 객체 생성
    @InjectMocks private MailService mailService;	            // @InjectMocks : 가짜 Mock 객체들을 자동으로 주입해주는 역할

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
      // given
      when(mailClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
              .thenReturn(true);
  
      // when
      boolean result = mailService.sendEmail("", "", "", "");
      Mockito.verify(mailSendRepository, times(1)).save(any(MailSendHistory.class));
  
      // when & then
      assertThat(result).isTrue();
    }
  
    @DisplayName("메일 전송 실패 테스트")
    @Test
    void sendMailFail() {
      // given
      when(mailClient.sendEmail(any(), any(), any(), any()))
              .thenThrow(new IllegalArgumentException("메일 전송"));
  
      // when & then
      assertThatThrownBy(() -> mailService.sendEmail("", "", "", ""))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessage("메일 전송");
    }
}
```

* Mock 객체는 테스트 작성을 위한 환경 구축이 복잡한 경우에 사용할 수 있는 `가짜 객체`를 말한다.
* Mock 객체는 테스트 대상 객체가 의존하는 객체를 가짜로 만들어 테스트 대상 객체의 의존 객체를 대체하는 것을 말하며 `@Mock` 어노테이션을 사용하면 쉽게 만들 수 있다.
* `@InjectMock` 어노테이션은 `@Mock` 어노테이션과 함께 사용되며, 테스트 대상 객체의 의존 객체를 자동으로 주입해주는 역할을 한다.

-----------------------
</details>