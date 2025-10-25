package com.jwj.db1.jdbc.transaction;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepository1;
import com.jwj.db1.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceV2 {

	private final DataSource dataSource;
	private final MemberRepositoryV2 memberRepositoryV2;

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		Connection con = dataSource.getConnection();
		try {
			con.setAutoCommit(false); //트랜잭션 시작
			// 비즈니스 로직
			bizLogic(con, fromId, toId, money);
			con.commit(); //성공시 커밋
		} catch (Exception e) {
			con.rollback(); //실패시 롤백
			throw new IllegalStateException(e);
		} finally {
			release(con);
		}
	}

	private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepositoryV2.findById(con, fromId);
		Member toMember = memberRepositoryV2.findById(con, toId);
		memberRepositoryV2.update(con, fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepositoryV2.update(con, toId, toMember.getMoney() + money);
	}

	private void release(Connection con) {
		if (con != null) {
			try {
				con.setAutoCommit(true); //커넥션 풀 고려
				con.close();
			} catch (Exception e) {
				log.info("error", e);
			}
		}
	}

	private void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("계좌이체중 예외 발생");
		}
	}
}
