### ✅Consider defining a bean of type 'com.querydsl.jpa.impl.JPAQueryFactory' in your configuration.

* QueryDSL 기반의 리포지토리 코드를 테스트하는 과정에서 JPAQueryFactory 빈을 등록해야 하는데, 그 설정이 누락되어 발생한 오류였다.
* 문제를 해결하기 위해서 Configuration 클래스에 JPAQueryFactory 빈을 만들어 등록해주면 된다.

```java
@Configuration
@EnableJpaAuditing
public class QueryDSLConfig {

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}
```