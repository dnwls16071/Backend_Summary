package com.jwj.querydsl_2nd.repository;

import com.jwj.querydsl_2nd.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {


	@Autowired EntityManager em;
	@Autowired MemberJpaRepository memberJpaRepository;

	@Test
	public void basicTest() {
		Member member = new Member("member1", 10);
		memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.findById(member.getId()).get();
		assertThat(findMember).isEqualTo(member);

		List<Member> all = memberJpaRepository.findAll();
		assertThat(all).containsExactly(member);

		List<Member> results = memberJpaRepository.findByUsername("member1");
		assertThat(results).containsExactly(member);
	}
}