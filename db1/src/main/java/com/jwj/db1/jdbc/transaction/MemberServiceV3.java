package com.jwj.db1.jdbc.transaction;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepositoryV2;
import com.jwj.db1.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceV3 {

	private final PlatformTransactionManager transactionManager;
	private final MemberRepositoryV3 memberRepository;

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		//트랜잭션 시작
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
		//비즈니스 로직
			bizLogic(fromId, toId, money);
			transactionManager.commit(status); //성공시 커밋
		} catch (Exception e) {
			transactionManager.rollback(status); //실패시 롤백
			throw new IllegalStateException(e);
		}
	}

	private void bizLogic(String fromId, String toId, int money) throws
			SQLException {
		Member fromMember = memberRepository.findById(fromId);
		Member toMember = memberRepository.findById(toId);
		memberRepository.update(fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(toId, toMember.getMoney() + money);
	}

	private void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("계좌이체중 예외 발생");
		}
	}
}
