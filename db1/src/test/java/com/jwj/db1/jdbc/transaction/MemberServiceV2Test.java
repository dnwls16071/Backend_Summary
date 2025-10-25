package com.jwj.db1.jdbc.transaction;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepositoryV2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.jwj.db1.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceV2Test {

	private MemberRepositoryV2 memberRepository;
	private MemberServiceV2 memberService;

	@BeforeEach
	void before() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		memberRepository = new MemberRepositoryV2(dataSource);
		memberService = new MemberServiceV2(dataSource, memberRepository);
	}

	@AfterEach
	void after() throws SQLException {
		memberRepository.delete("memberA");
		memberRepository.delete("memberB");
		memberRepository.delete("ex");
	}

	@Test
	@DisplayName("정상 이체")
	void accountTransfer() throws SQLException {

		//given
		Member memberA = new Member("memberA", 10000);
		Member memberB = new Member("memberB", 10000);
		memberRepository.save(memberA);
		memberRepository.save(memberB);

		//when
		memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

		//then
		Member findMemberA = memberRepository.findById(memberA.getMemberId());
		Member findMemberB = memberRepository.findById(memberB.getMemberId());
		assertThat(findMemberA.getMoney()).isEqualTo(8000);
		assertThat(findMemberB.getMoney()).isEqualTo(12000);
	}
}