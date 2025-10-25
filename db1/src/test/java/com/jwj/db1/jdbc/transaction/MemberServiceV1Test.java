package com.jwj.db1.jdbc.transaction;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepository1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.jwj.db1.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceV1Test {

	public static final String MEMBER_A = "memberA";
	public static final String MEMBER_B = "memberB";
	public static final String MEMBER_EX = "ex";

	private MemberRepository1 memberRepository;
	private MemberServiceV1 memberService;

	@BeforeEach
	void before() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		memberRepository = new MemberRepository1(dataSource);
		memberService = new MemberServiceV1(memberRepository);
	}

	@AfterEach
	void after() throws SQLException {
		memberRepository.delete(MEMBER_A);
		memberRepository.delete(MEMBER_B);
		memberRepository.delete(MEMBER_EX);
	}

	@Test
	@DisplayName("정상 이체")
	void accountTransfer() throws SQLException {

		//given
		Member memberA = new Member(MEMBER_A, 10000);
		Member memberB = new Member(MEMBER_B, 10000);
		memberRepository.save(memberA);
		memberRepository.save(memberB);

		//when
		memberService.accountTransfer(memberA.getMemberId(),
				memberB.getMemberId(), 2000);

		//then
		Member findMemberA = memberRepository.findById(memberA.getMemberId());
		Member findMemberB = memberRepository.findById(memberB.getMemberId());
		assertThat(findMemberA.getMoney()).isEqualTo(8000);
		assertThat(findMemberB.getMoney()).isEqualTo(12000);
	}
}