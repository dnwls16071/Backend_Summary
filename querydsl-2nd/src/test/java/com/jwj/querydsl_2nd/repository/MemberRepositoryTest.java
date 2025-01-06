package com.jwj.querydsl_2nd.repository;

import com.jwj.querydsl_2nd.dto.MemberTeamDto;
import com.jwj.querydsl_2nd.entity.Member;
import com.jwj.querydsl_2nd.entity.QMember;
import com.jwj.querydsl_2nd.entity.Team;
import com.jwj.querydsl_2nd.util.MemberSearchCond;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired private EntityManager em;
	private JPAQueryFactory queryFactory;
	@Autowired private MemberRepository memberRepository;

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
	void searchSimple() {
		MemberSearchCond memberSearchCond = new MemberSearchCond();
		PageRequest pageRequest = PageRequest.of(0, 3);

		Page<MemberTeamDto> result = memberRepository.searchPageSimple(memberSearchCond, pageRequest);
		assertThat(result.getSize()).isEqualTo(3);
		assertThat(result.getContent()).extracting("username")
				.containsExactly("member1", "member2", "member3");
	}

	@Test
	void querydslPredicateExecutorTest() {

		QMember member = QMember.member;
		Iterable<Member> result = memberRepository.findAll(member.age.between(10, 40).and(member.username.eq("member1")));

		for (Member m : result) {
			System.out.println("m = " + m);
		}
	}
}