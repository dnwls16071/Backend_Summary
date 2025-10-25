package com.jwj.springdatajpa.repository;

import com.jwj.springdatajpa.entity.Member;
import com.jwj.springdatajpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@PersistenceContext private EntityManager em;
	@Autowired private MemberRepository memberRepository;
	@Autowired private TeamRepository teamRepository;

	@Test
	@DisplayName("엔티티 그래프 예제")
	void testEntityGraph() {

        Team teamA = new Team();
		teamA.setName("teamA");
		teamRepository.save(teamA);

		Team teamB = new Team();
		teamB.setName("teamB");
		teamRepository.save(teamB);

		Member member1 = new Member();
		member1.setName("member1");
		member1.setAge(10);
		member1.setTeam(teamA);
		memberRepository.save(member1);

		Member member2 = new Member();
		member2.setName("member2");
		member2.setAge(10);
		member2.setTeam(teamB);
		memberRepository.save(member2);

        em.flush();
		em.clear();

		//List<Member> members = memberRepository.findAll();	// MEMBER SELECT 쿼리 1번
		//List<Member> members = memberRepository.findMemberFetchJoin();
		List<Member> members = memberRepository.findMemberEntityGraph();

		for (Member member : members) {
			System.out.println("member = " + member.getName());	// 여기선 쿼리가 안 나감
			System.out.println("member.team = " + member.getTeam().getName()); // 여기선 프록시 초기화
		}
	}
}