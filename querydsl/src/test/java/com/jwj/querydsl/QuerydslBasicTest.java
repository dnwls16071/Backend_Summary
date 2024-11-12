package com.jwj.querydsl;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.QMember;
import com.jwj.querydsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.jwj.querydsl.entity.QMember.member;
import static com.jwj.querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuerydslBasicTest {

	@Autowired private EntityManager em;
	private JPAQueryFactory queryFactory;

	@BeforeEach
	public void before() {
		queryFactory = new JPAQueryFactory(em);
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 member1을 찾을 수 있다.")
	void QueryDSL을_사용하여_member1을_찾을_수_있다() {
		// 별칭
		QMember m = new QMember("m");

		// when
		Member findMember = queryFactory
				.select(m)
				.from(m)
				.where(m.username.eq("member1"))
				.fetchOne();

		// then
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 member1을 검색할 수 있다.")
	void  QueryDSL을_사용하여_member1을_검색할_수_있다() {
		// given
		QMember member = QMember.member;

		// when
		Member findMember = queryFactory
				.selectFrom(member)
				.where(member.username.eq("member1").and(member.age.eq(10)))
				.fetchOne();

		// then
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	@DisplayName("QueryDSL에서_여러_조건을_조합하여_조회할_수_있다.")
	void  QueryDSL에서_여러_조건을_조합하여_조회할_수도_있다() {
		// given
		QMember member = QMember.member;

		// when
		Member findMember = queryFactory
				.selectFrom(member)
				.where(
						member.username.eq("member1"),
						member.age.eq(10))
				.fetchOne();

		// then
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	@DisplayName("QueryDSL은 상황에 따라 적절한 조회 메서드를 선택할 수 있다")
	void QueryDSL은_상황에_따라_적절한_조회_메서드를_선택할_수_있다() {
		// List
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.fetch();

		// 단일 건
		Member fetchOne = queryFactory
				.selectFrom(member)
				.fetchOne();

		// 맨 처음 한 건
		Member fetchFirst = queryFactory
				.selectFrom(member)
				.fetchFirst();

		// 페이징에서 사용
		QueryResults<Member> results = queryFactory
				.selectFrom(member)
				.fetchResults();

		// Count용 쿼리로 변경
		long l = queryFactory
				.selectFrom(member)
				.fetchCount();
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 정렬을 할 수 있다.")
	void QueryDSL을_사용하여_정렬을_할_수_있다() {
		// given
		em.persist(new Member(null, 100));
		em.persist(new Member("member5", 100));
		em.persist(new Member("member6", 100));

		// when
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.where(member.age.eq(100))
				.orderBy(member.age.desc(), member.username.asc().nullsLast())
				.fetch();

		// then
		assertThat(fetch).hasSize(3);
		assertThat(fetch.get(0).getUsername()).isEqualTo("member5");
		assertThat(fetch.get(1).getUsername()).isEqualTo("member6");
		assertThat(fetch.get(2).getUsername()).isEqualTo(null);
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 페이징 처리를 할 수 있다 (1).")
	void QueryDSL을_사용하여_페이징_처리를_할_수_있다1() {
		// when
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.orderBy(member.username.desc())
				.offset(1)
				.limit(2)
				.fetch();

		// then
		assertThat(fetch.size()).isEqualTo(2);
	}

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

	@Test
	@DisplayName("QueryDSL에서 집합 함수를 사용할 수 있다.")
	void QueryDSL을_사용하여_집합_함수를_사용할_수_있다() {
		// when
		List<Tuple> fetch = queryFactory
				.select(
						member.count(),
						member.age.sum(),
						member.age.avg(),
						member.age.max(),
						member.age.min()
				)
				.from(member)
				.fetch();

		Tuple tuple = fetch.get(0);

		// then
		assertThat(tuple.get(member.count())).isEqualTo(4);
		assertThat(tuple.get(member.age.sum())).isEqualTo(100);
		assertThat(tuple.get(member.age.avg())).isEqualTo(25);
		assertThat(tuple.get(member.age.max())).isEqualTo(40);
		assertThat(tuple.get(member.age.min())).isEqualTo(10);
	}

	@Test
	@DisplayName("QueryDSL에서 GroupBy를 사용할 수 있다.")
	void QueryDSL에서_GroupBy를_사용할_수_있다() {
		// 팀의 이름과 각 팀의 평균 연령
		// when
		List<Tuple> fetch = queryFactory
				.select(team.name, member.age.avg())
				.from(member)
				.join(member.team, team)
				.groupBy(team.name)
				.fetch();

		Tuple teamA = fetch.get(0);
		Tuple teamB = fetch.get(1);

		// then
		assertThat(teamA.get(team.name)).isEqualTo("teamA");
		assertThat(teamA.get(member.age.avg())).isEqualTo(15);
		assertThat(teamB.get(team.name)).isEqualTo("teamB");
		assertThat(teamB.get(member.age.avg())).isEqualTo(35);
	}

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

	@Test
	@DisplayName("회원의 이름과 팀 이름이 같은 대상에 대한 외부 조인을 할 수 있다.")
	void 회원의_이름과_팀_이름이_같은_대상에_대한_외부_조인을_할_수_있다() {
		em.persist(new Member("teamA"));
		em.persist(new Member("teamB"));
		em.persist(new Member("teamC"));

		List<Tuple> fetch = queryFactory
				.select(member, team)
				.from(member)
				.leftJoin(team).on(member.username.eq(team.name))
				.fetch();

		for (Tuple tuple : fetch) {
			System.out.println("tuple = " + tuple);
		}
	}

	@PersistenceUnit
	EntityManagerFactory emf;

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
}
