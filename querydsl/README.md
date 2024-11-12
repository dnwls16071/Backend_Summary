### ✅ SpringBoot 3.x Version에서 QueryDSL 적용 방법 정리하기

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

* 김영한 강사님의 스프링 QueryDSL 강좌를 수강하면서 QueryDSL 설정을 하는 과정이 상당히 까다로워 이번 기회에 미리 정리를 해보고자 한다.

> 프로젝트 생성 시 Gradle 버전과 SpringBoot 버전
>
> * Gradle 8.10.2
> * SpringBoot 3.3.5

```java
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.3.5'
	id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.jwj'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	runtimeOnly 'com.mysql:mysql-connector-j'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
            
    // 테스트 Lombok
	testCompileOnly 'org.projectlombok:lombok'
	testAnnotationProcessor 'org.projectlombok:lombok'

    // QueryDSL
	implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
	annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
	annotationProcessor "jakarta.annotation:jakarta.annotation-api"
	annotationProcessor "jakarta.persistence:jakarta.persistence-api"
}

tasks.named('test') {
	useJUnitPlatform()
}

clean {
	delete file('src/main/generated')
}
```

#### Hello Entity 작성하여 테스트하기

```java
@Entity
@Getter
@Setter
public class Hello {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
}
```

#### QueryDSL에 의해 만들어지는 QueryDSL 클래스 확인하기(/src/main/generated)

```java
/**
 * QHello is a Querydsl query type for Hello
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHello extends EntityPathBase<Hello> {

    private static final long serialVersionUID = 987045975L;

    public static final QHello hello = new QHello("hello");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QHello(String variable) {
        super(Hello.class, forVariable(variable));
    }

    public QHello(Path<? extends Hello> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHello(PathMetadata metadata) {
        super(Hello.class, metadata);
    }

}
```

-----------------------
</details>

### ✅QueryDSL에서 Count 쿼리 작성 요령

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

* 아래와 같은 테스트 코드를 작성하면서 실행되는 쿼리의 결과문은 다음과 같았다.

```java
@Test
@DisplayName("QueryDSL을 사용하여 페이징을 할 수 있다 (2).")
void QueryDSL을_사용하여_페이징을_할_수_있다2() {
    // when
    QueryResults<Member> results = queryFactory
            .selectFrom(member)
            .orderBy(member.username.desc())
            .offset(1)
            .limit(2)
            .fetchResults();

    // then
    assertThat(results.getTotal()).isEqualTo(4);
    assertThat(results.getLimit()).isEqualTo(2);
    assertThat(results.getOffset()).isEqualTo(1);
    assertThat(results.getResults().size()).isEqualTo(2);
}
```

#### 쿼리 결과문

```sql
Hibernate: 
    select
        count(m1_0.member_id) 
    from
        member m1_0

// ...
    
select count(m1_0.member_id) from member m1_0
select count(m1_0.member_id) from member m1_0;
Hibernate:
select
    m1_0.member_id,
    m1_0.age,
    m1_0.team_id,
    m1_0.username
from
    member m1_0
order by
    m1_0.username desc
    limit
        ?, ?
```

* 페이징 쿼리를 작성할 때, 데이터를 조회하는 쿼리는 여러 테이블을 조인해야 하지만, count 쿼리는 조인이 필요 없는 경우도 있다.
* 그러나 이렇게 자동화된 count 쿼리의 경우 원본 쿼리와 같이 모두 조인을 해버리기 때문에 기대했던 성능이 나오지 않을 수 있다.
* count 쿼리에 조인이 필요없는 성능 최적화가 필요하다면 count 전용 쿼리를 별도로 작성해야 한다.

-----------------------
</details>

### ✅QueryDSL 조인(기본 조인, ON절, 페치 조인)

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

* 조인의 대상은 외래 키를 관리하는 곳, 즉 외래 키를 가지는 테이블을 말한다.
* QueryDSL에서 조인의 기본 문법은 첫 번째 파라미터에 조인 대상을 지정하고, 두 번째 파라미터에 별칭(alias)으로 사용할 Q타입을 지정하면 된다.

> join(조인 대상, 별칭으로 사용할 Q타입)

#### 기본 조인

```java
@Test
@DisplayName("teamA에 소속된 모든 회원을 조회할 수 있다.")
void teamA에_소속된_모든_회원을_조회할_수_있다() {
	// when
	List<Member> fetch = queryFactory
			.selectFrom(member)
			.join(member.team, team)
			.where(team.name.eq("teamA"))
			.fetch();

	// then
	assertThat(fetch)
            .extracting("username", "age")
            .containsExactlyInAnyOrder(
                    tuple("member1", 10),
                    tuple("member2", 20)
        );
}
```

#### ON절

```java
@Test
@DisplayName("회원과 팀을 조인하면서 팀 이름이 teamA인 팀만 조인하고 회원은 모두 조회한다. (1)")
void 회원과_팀을_조인하면서_팀_이름이_teamA인_팀만_조인하고_회원은_모두_조회한다1() {
    // when
    // inner join 적용 - WHERE 절을 사용
    List<Tuple> fetch = queryFactory
            .select(member, team)
            .from(member)
            .join(member.team, team)
            .where(team.name.eq("teamA"))
            .fetch();

    // then
    for (Tuple tuple : fetch) {
        System.out.println("tuple = " + tuple);
    }
}

@Test
@DisplayName("회원과 팀을 조인하면서 팀 이름이 teamA인 팀만 조인하고 회원은 모두 조회한다. (2)")
void 회원과_팀을_조인하면서_팀_이름이_teamA인_팀만_조인하고_회원은_모두_조회한다2() {
    // when
    // left join 적용 - ON절을 사용
    List<Tuple> fetch = queryFactory
            .select(member, team)
            .from(member)
            .leftJoin(member.team, team).on(team.name.eq("teamA"))
            .fetch();

    // then
    for (Tuple tuple : fetch) {
        System.out.println("tuple = " + tuple);
    }
}
```

-----------------------
</details>