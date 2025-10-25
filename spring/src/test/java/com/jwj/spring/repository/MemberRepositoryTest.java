package com.jwj.spring.repository;

import com.jwj.spring.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;

	@Transactional
	@Test
	void memberTest() {
		Member member = new Member("idA", "memberA");

		memberRepository.initTable();
		memberRepository.save(member);
		Member findMember = memberRepository.find(member.getMemberId());
		assertThat(findMember).isEqualTo(member);
	}
}