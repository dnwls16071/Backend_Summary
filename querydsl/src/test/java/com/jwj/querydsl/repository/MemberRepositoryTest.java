package com.jwj.querydsl.repository;

import com.jwj.querydsl.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired EntityManager em;
	@Autowired MemberRepository memberRepository;

	@Test
	void basicTest() {
		Member member = new Member("member1", 10);
		memberRepository.save(member);

		Member findMember = memberRepository.findById(member.getId()).get();
		assertThat(findMember).isEqualTo(member);

		List<Member> all = memberRepository.findAll();
		assertThat(all).containsExactly(findMember);

		List<Member> list = memberRepository.findByUsername("member1");
		assertThat(list).hasSize(1);
	}
}