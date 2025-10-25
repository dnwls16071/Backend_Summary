package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.Team;
import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

	@Autowired MemberJpaRepository memberJpaRepository;
	@Autowired EntityManager em;

	@Test
	@DisplayName("리포지토리에 회원을 저장할 수 있다")
	void save() {
		Member findMember = new Member("member1", 10);
		memberJpaRepository.save(findMember);

		Member member = memberJpaRepository.findById(findMember.getId()).get();
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 동적 쿼리를 작성할 수 있다.")
	void searchTest1() {
		// given
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

		// when
		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setTeamName("teamB");

		List<MemberTeamDTO> memberTeamDTOS = memberJpaRepository.searchByBuilder(condition);

		// then
		assertThat(memberTeamDTOS)
				.extracting("username", "age")
				.containsExactlyInAnyOrder(
						tuple("member3", 30),
						tuple("member4", 40)
				);
	}

	@Test
	@DisplayName("QueryDSL을 사용하여 동적 쿼리를 작성할 수 있다.")
	void searchTest2() {
		// given
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

		// when
		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setTeamName("teamB");

		List<MemberTeamDTO> memberTeamDTOS = memberJpaRepository.searchByWhere(condition);

		// then
		assertThat(memberTeamDTOS)
				.extracting("username", "age")
				.containsExactlyInAnyOrder(
						tuple("member3", 30),
						tuple("member4", 40)
				);
	}
}