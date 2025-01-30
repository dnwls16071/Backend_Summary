package com.jwj.db1.jdbc.connection.repository;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepositoryV0;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {

	MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

	@Test
	void crud() throws SQLException {
		Member memberV0 = new Member("memberV0", 10000);
		repositoryV0.save(memberV0);

		Member findMember = repositoryV0.findById(memberV0.getMemberId());
		log.info("findMember={}", findMember);
		assertThat(findMember).isEqualTo(memberV0);

		repositoryV0.update(memberV0.getMemberId(), 20000);
		Member updatedMember = repositoryV0.findById(memberV0.getMemberId());
		assertThat(updatedMember.getMoney()).isEqualTo(20000);

		repositoryV0.delete(memberV0.getMemberId());
		assertThatThrownBy(() -> repositoryV0.findById(memberV0.getMemberId()))
				.isInstanceOf(NoSuchElementException.class);
	}
}