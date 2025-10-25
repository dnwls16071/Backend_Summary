package com.jwj.querydsl_2nd.entity;

import com.jwj.querydsl_2nd.dto.MemberDto;
import com.jwj.querydsl_2nd.dto.MemberTeamDto;
import com.jwj.querydsl_2nd.dto.QMemberDto;
import com.jwj.querydsl_2nd.repository.MemberJpaRepository;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jwj.querydsl_2nd.entity.QMember.member;
import static com.jwj.querydsl_2nd.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberTest {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@PersistenceContext
	private EntityManager em;
	JPAQueryFactory queryFactory;
	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@BeforeEach
	public void before() {
		queryFactory = new JPAQueryFactory(em);  // QueryDsl용 필드 초기화

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

		em.persist(new Member(null, 100));
		em.persist(new Member("member5", 100));
		em.persist(new Member("member6", 100));

		// 초기화
		em.flush();
		em.clear();
	}

	@Test
	public void JPQLCode() {
		Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
				.setParameter("username", "member1")
				.getSingleResult();

		assertThat(result.getUsername()).isEqualTo("member1");
	}

	@Test
	public void QuerydslCode() {

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember m = new QMember("m");	// 별칭

		Member member = queryFactory
				.select(m)
				.from(m)
				.where(m.username.eq("member1"))
				.fetchOne();

		assertThat(member.getUsername()).isEqualTo("member1");
	}

	@Test
	public void search() {
		Member findMember = queryFactory
				.selectFrom(member)    // select()와 from() 합친 형태
				.where(member.username.eq("member1").and(member.age.eq(10)))
				.fetchOne();
		assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	@Test
	public void resultFetch() {
		List<Member> fetch = queryFactory
				.selectFrom(member)
				.fetch();

		Member fetchOne = queryFactory
				.selectFrom(member)
				.fetchOne();

		QueryResults<Member> results = queryFactory
				.selectFrom(member)
				.fetchResults();

		results.getTotal();
		List<Member> content = results.getResults();

		long total = queryFactory
				.selectFrom(member)
				.fetchCount();
	}

	@Test
	public void sort() {

		List<Member> result = queryFactory
				.selectFrom(member)
				.where(member.age.eq(100))
				.orderBy(member.age.desc(), member.username.asc().nullsLast())
				.fetch();

		assertThat(result.get(0).getUsername()).isEqualTo("member5");
		assertThat(result.get(1).getUsername()).isEqualTo("member6");
		assertThat(result.get(2).getUsername()).isEqualTo(null);
	}

	@Test
	public void paging1() {
		List<Member> result = queryFactory
				.selectFrom(member)
				.orderBy(member.username.desc())
				.offset(1)
				.limit(2)
				.fetch();
	}

	@Test
	public void aggregation() {

		List<Tuple> result = queryFactory
				.select(member.count(),
						member.age.sum(),
						member.age.avg(),
						member.age.max(),
						member.age.min()
				)
				.from(member)
				.fetch();
	}

	@Test
	public void group() {
		List<Tuple> result = queryFactory
				.select(team.name, member.age.avg())    // 팀 이름과 평균 연령
				.from(member)
				.join(member.team, team)
				.groupBy(team.name)                        // 팀 이름 그룹화
				.fetch();
	}

	@Test
	public void join() {
		List<Member> result = queryFactory
				.selectFrom(member)
				.join(member.team, team)
				.where(team.name.eq("teamA"))
				.fetch();
	}

	@Test
	public void join_on_filtering() {
		List<Tuple> result = queryFactory
				.select(member, team)
				.from(member)
				.leftJoin(member.team, team)
				.on(team.name.eq("teamA"))
				.fetch();
	}

	@Test
	public void fetchJoin() {
		em.flush();
		em.clear();

		Member findMember = queryFactory
				.selectFrom(member)
				.join(member.team, team).fetchJoin()
				.where(member.username.eq("member1"))
				.fetchOne();

		boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
		assertThat(loaded).as("페치 조인 적용").isTrue();
	}

	@Test
	public void subQuery() {

		QMember memberSub = new QMember("memberSub");

		// 바깥 루프와 안쪽 루프의 Member 타입은 달라야 한다.
		List<Member> result = queryFactory
				.selectFrom(member)
				.where(member.age.eq(
						JPAExpressions
								.select(memberSub.age.max())
								.from(memberSub)
				))
				.fetch();
	}

	@Test
	public void caseTest() {

		queryFactory
				.select(new CaseBuilder()
						.when(member.age.between(0, 20)).then("0~20살")
						.when(member.age.between(21, 30)).then("21~30살")
						.otherwise("기타")
				)
				.from(member)
				.fetch();

		queryFactory
				.select(member.age
						.when(10).then("열살")
						.when(20).then("스무살")
						.otherwise("기타")
				)
				.from(member)
				.fetch();

	}

	@Test
	public void singleProjection() {
		List<String> result = queryFactory
				.select(member.username)
				.from(member)
				.fetch();
	}

	@Test
	public void tupleProjection() {
		List<Tuple> result = queryFactory
				.select(member.username, member.age)
				.from(member)
				.fetch();

		for (Tuple tuple : result) {
			String username = tuple.get(member.username);
			Integer age = tuple.get(member.age);
			System.out.println("username = " + username);
			System.out.println("age = " + age);
		}
	}

	@Test
	public void dtoProjection() {
		List<MemberDto> results = em.createQuery("select new com.jwj.querydsl_2nd.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
				.getResultList();

	}

	@Test
	public void dtoProjectionByQuerydsl() {
		List<MemberDto> results = queryFactory
				.select(Projections.bean(MemberDto.class,
						member.username,
						member.age))
				.from(member)
				.fetch();
	}

	@Test
	public void QueryProjection() {
		List<MemberDto> results = queryFactory
				.select(new QMemberDto(member.username, member.age))
				.from(member)
				.fetch();
	}

	@Test
	public void dynamicQueryByBooleanBuilder() {
		String usernameParam = "member1";
        Integer ageParam = 10;

		List<Member> members = searchMember1(usernameParam, ageParam);
		assertThat(members.size()).isEqualTo(1);
	}

	private List<Member> searchMember1(String usernameCond, Integer ageCond) {

		BooleanBuilder builder = new BooleanBuilder();
		if (usernameCond != null) {
			builder.and(member.username.eq(usernameCond));
		}

		if (ageCond != null) {
			builder.and(member.age.eq(ageCond));
		}

		return queryFactory
				.selectFrom(member)
				.where(builder)
				.fetch();
	}

	@Test
	public void dynamicQueryByWhereParam() {
		String usernameParam = "member1";
		Integer ageParam = 10;

		List<Member> members = searchMember2(usernameParam, ageParam);
		assertThat(members.size()).isEqualTo(1);
	}

	private List<Member> searchMember2(String usernameCond, Integer ageCond) {
		return queryFactory
				.selectFrom(member)
				.where(usernameEq(usernameCond), ageEq(ageCond))
				.fetch();
	}

	private Predicate ageEq(Integer ageCond) {
		if (ageCond != null) {
			return member.age.eq(ageCond);
		}
		return null;
	}

	private Predicate usernameEq(String usernameCond) {
		if (usernameCond != null) {
			return member.username.eq(usernameCond);
		}
		return null;
	}

	@Test
	public void bulk() {
		long count = queryFactory
				.update(member)
				.set(member.username, "비회원")
				.where(member.age.eq(20))
				.execute();
	}

	@Test
	public void booleanBuilder() {
		MemberSearchCond memberSearchCond = new MemberSearchCond();
		memberSearchCond.setAgeGoe(35);
		memberSearchCond.setAgeLoe(40);
		memberSearchCond.setTeamName("teamB");

		List<MemberTeamDto> memberTeamDtos = memberJpaRepository.searchByBuilder(memberSearchCond);
	}

	@Test
	public void whereParam() {
		MemberSearchCond memberSearchCond = new MemberSearchCond();
		memberSearchCond.setAgeGoe(35);
		memberSearchCond.setAgeLoe(40);
		memberSearchCond.setTeamName("teamB");

		List<MemberTeamDto> search = memberJpaRepository.search(memberSearchCond);
	}
}
