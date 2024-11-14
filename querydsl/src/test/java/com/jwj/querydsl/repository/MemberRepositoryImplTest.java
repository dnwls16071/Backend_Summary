package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.Member;
import com.jwj.querydsl.entity.Team;
import com.jwj.querydsl.entity.dto.MemberSearchCondition;
import com.jwj.querydsl.entity.dto.MemberTeamDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryImplTest {

	@Autowired private EntityManager em;
	@Autowired private MemberRepositoryImpl memberRepository;

	@Test
	void searchTest() {
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
		PageRequest request = PageRequest.of(0, 3);
		Page<MemberTeamDTO> result = memberRepository.searchPageSimple(condition, request);

		// then
		assertThat(result).hasSize(3);
		assertThat(result.getContent())
				.extracting("username")
				.containsExactlyInAnyOrder("member1", "member2", "member3");
	}
}