package com.jwj.db1.jdbc.transaction;

import com.jwj.db1.jdbc.domain.Member;
import com.jwj.db1.jdbc.repository.MemberRepository1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class MemberServiceV1 {

	private final MemberRepository1 memberRepository1;

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository1.findById(fromId);
		Member toMember = memberRepository1.findById(toId);

		memberRepository1.update(fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository1.update(toId, toMember.getMoney() + money);
	}

	private void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("계좌이체중 예외 발생");
		}
	}
}
