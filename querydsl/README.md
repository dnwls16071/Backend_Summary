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

  id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
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

  testCompileOnly 'org.projectlombok:lombok'
  testAnnotationProcessor 'org.projectlombok:lombok'

  implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
  annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
  annotationProcessor "jakarta.annotation:jakarta.annotation-api"
  annotationProcessor "jakarta.persistence:jakarta.persistence-api"
  annotationProcessor "jakarta.annotation:jakarta.annotation-api"
}

tasks.named('test') {
  useJUnitPlatform()
}
clean {
	delete file ('src/main/generated')

	def querydslDir = "$buildDir/generated/querydsl"

	sourceSets {
		main.java.srcDir querydslDir
	}

	configurations {
		compileOnly {
			extendsFrom annotationProcessor
		}
		querydsl.extendsFrom compileClasspath
	}
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

#### 페치 조인(fetch join)

* 페치 조인은 SQL에서 제공하는 기능은 아니다. SQL 조인을 활용해서 연관된 엔티티를 SQL 한 번에 조회하는 기능이다.
* 주로 성능 최적화에 사용되는 방법이다.

```java
@PersistenceUnit EntityManagerFactory emf;

@Test
@DisplayName("페치조인을 적용하지 않은 테스트 코드")
void 페치조인을_적용하지_않은_테스트_코드() {
    em.flush();			// 쓰기 지연 SQL문 저장소 반영
    em.clear(); 		// 영속성 컨텍스트 비우기

    Member findMember = queryFactory
            .selectFrom(member)
            .where(member.username.eq("member1"))
            .fetchOne();

    boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
    assertThat(isLoaded).isFalse();
}
```

쿼리문 로그

```sql
Hibernate: 
    select
        m1_0.member_id,
        m1_0.age,
        m1_0.team_id,
        m1_0.username 
    from
        member m1_0 
    where
        m1_0.username=?
```

* 페치 조인을 사용하지 않고 QueryDSL문을 돌려 보면 현재 멤버와 연관 관계를 맺고 있는 팀 엔티티는 조회가 되지 않는 것을 볼 수 있다.
* 만약 팀 엔티티와 관련된 데이터를 조회하려면 결국 쿼리를 2번 호출해야 한다.

```java
@Test
@DisplayName("페치조인을 적용한 테스트 코드")
void 페치조인을_적용한_테스트_코드() {
    em.flush();        // 쓰기 지연 SQL문 저장소 반영
    em.clear();        // 영속성 컨텍스트 비우기

    Member findMember = queryFactory
            .selectFrom(member)
            .join(member.team, team).fetchJoin()
            .where(member.username.eq("member1"))
            .fetchOne();

    boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
    assertThat(isLoaded).isTrue();
}
```

쿼리문 로그

```sql
Hibernate: 
    select
        m1_0.member_id,
        m1_0.age,
        t1_0.team_id,
        t1_0.name,
        m1_0.username 
    from
        member m1_0 
    join
        team t1_0 
            on t1_0.team_id=m1_0.team_id 
    where
        m1_0.username=?
```

-----------------------
</details>

### ✅QueryDSL 서브 쿼리

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

```java
@Test
@DisplayName("나이가 가장 많은 회원을 조회한다.")
void 나이가_가장_많은_회원을_조회한다() {
    // given
    QMember memberSubquery = new QMember("memberSubquery");	// alias 별칭 중복 방지

    // when
    // eq(equal) : 일치
    // goe(greater or equal) : 크거나 같음
    // loe(less or equal) : 작거나 같음
    // gt(greater than) : 크다
    // lt(less than) : 작다
    List<Member> fetch = queryFactory
            .selectFrom(member)
            .where(member.age.eq(
                    JPAExpressions
                            .select(memberSubquery.age.max())
                            .from(memberSubquery)
            ))
            .fetch();

    // then
    assertThat(fetch).extracting("age")
            .containsExactlyInAnyOrder(30, 40);
}
```

* JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리는 지원되지 않는다. 이는 QueryDSL 역시 마찬가지다.
* from 절의 서브쿼리 해결방안으로 다음과 같이 3가지가 있다.
  * 서브쿼리는 join으로 변경한다.
  * 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
  * nativeSQL을 사용한다.

-----------------------
</details>

### ✅QueryDSL 프로젝션

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

#### 단일 프로젝션

```java
@Test
@DisplayName("QueryDSL - 프로젝션 (1)")
void QueryDSL_프로젝션1() {
    // when
    List<Member> fetch = queryFactory
            .select(member)
            .from(member)
            .fetch();

    for (Member m : fetch) {
        System.out.println("m = " + m);
    }
}
```

#### 튜플 프로젝션

```java
@Test
@DisplayName("QueryDSL - 프로젝션 (2)")
void QueryDSL_프로젝션2() {
    // when
    List<Tuple> fetch = queryFactory
            .select(member.username, member.age)
            .from(member)
            .fetch();

    for (Tuple tuple : fetch) {
        System.out.println("tuple = " + tuple);
    }
}
```

#### DTO 프로젝션

* `setter`를 사용하는 방법

```java
@Test
@DisplayName("QueryDSL - DTO 프로젝션 (1)")
void QueryDSL_DTO_프로젝션1() {
    // when
    List<MemberDTO> fetch = queryFactory
            .select(Projections.bean(MemberDTO.class, member.username, member.age))
            .from(member)
            .fetch();

    for (MemberDTO memberDTO : fetch) {
        System.out.println("memberDTO = " + memberDTO);
    }
}
```

* `fields`를 사용하는 방법

```java
@Test
@DisplayName("QueryDSL - DTO 프로젝션 (2)")
void QueryDSL_DTO_프로젝션2() {
    // when
    List<MemberDTO> fetch = queryFactory
          .select(Projections.fields(MemberDTO.class, member.username, member.age))
          .from(member)
          .fetch();

    for (MemberDTO memberDTO : fetch) {
        System.out.println("memberDTO = " + memberDTO);
    }
}
```

* `constructor`를 사용하는 방법

```java
@Test
@DisplayName("QueryDSL - DTO 프로젝션 (3)")
void QueryDSL_DTO_프로젝션3() {
    // when
    List<MemberDTO> fetch = queryFactory
            .select(Projections.constructor(MemberDTO.class, member.username, member.age))
            .from(member)
            .fetch();

    for (MemberDTO memberDTO : fetch) {
        System.out.println("memberDTO = " + memberDTO);
    }
}
```

* `@QueryProjection`을 사용하는 방법
  * DTO에 QueryDSL 어노테이션을 유지해야 한다는 점과 DTO까지 Q 파일을 생성해야 한다는 단점이 있다.

```java
@Test
@DisplayName("QueryDSL - @QueryProjection 사용")
void QueryDSL_QueryProjection_사용() {
    // when
    List<MemberDTO> fetch = queryFactory
            .select(new QMemberDTO(member.username, member.age))
            .from(member)
            .fetch();

    for (MemberDTO memberDTO : fetch) {
        System.out.println("memberDTO = " + memberDTO);
    }
}
```

-----------------------
</details>

### ✅ 벌크 연산(Bulk)

<details>
   <summary> 정리 (👈 Click)</summary>
<br />

```java
@Test
@Commit
@DisplayName("QueryDSL - 수정 쿼리 작성")
void QueryDSL_수정_쿼리_작성() {
    // when
    // update() : bulk operation
    long count = queryFactory
            .update(member)
            .set(member.username, "비회원")
            .where(member.age.lt(30))
            .execute();

    // then
    List<Member> fetch = queryFactory
            .selectFrom(member)
            .fetch();

    for (Member m : fetch) {
        System.out.println("m = " + m);
    }
}
```

* 먼저 벌크 연산을 수행하게 된다. → 30살보다 적은 멤버에 대해서 이름을 비회원으로 바꾸는 연산을 수행
* 벌크 연산은 JPA의 영속성 컨텍스트를 무시하고 바로 데이터베이스에 반영된다.
* `then` 부분에서 JpaQueryFactory로 조회를 수행할 경우 JPA의 영속성 컨텍스트가 비워진 상태가 아니기 때문에 DB로부터 조회하지 않고 영속성 컨텍스트에 있는 값을 조회하게 된다.
* 결과를 출력하면 DB에 반영된 결과와 일치하지 않는다는 것을 볼 수 있다.
* 이와 같이 벌크 연산의 경우 JPA 영속성 컨텍스트와 DB간 데이터 불일치 문제가 발생할 가능성이 높다.
* 따라서 이런 문제를 해결하기 위해 벌크 연산을 수행한 후 영속성 컨텍스트를 초기화해주는 작업을 해주는 것이 좋다.
* 코드를 개선하면 아래와 같이 개선할 수 있다.

```java
@Test
@Commit
@DisplayName("QueryDSL - 수정 쿼리 작성")
void QueryDSL_수정_쿼리_작성() {
    // when
    // update() : bulk operation
    long count = queryFactory
            .update(member)
            .set(member.username, "비회원")
            .where(member.age.lt(30))
            .execute();

	em.flush();     
	em.clear();
	
    // then
    List<Member> fetch = queryFactory
            .selectFrom(member)
            .fetch();

    for (Member m : fetch) {
        System.out.println("m = " + m);
    }
}
```

-----------------------
</details>

### ✅ 순수 JPA 리포지토리와 QueryDSL 비교

<details>
   <summary> 정리 (👈 Click)</summary>
<br />



-----------------------
</details>