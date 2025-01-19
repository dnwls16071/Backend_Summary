package com.jwj.springPrinciple.repository;

import com.jwj.springPrinciple.entity.Member;

import java.util.HashMap;
import java.util.Map;

public class MemberRepositoryImpl implements MemberRepository {

	private static Map<Long, Member> memberMap = new HashMap<>();

	@Override
	public void save(Member member) {
		memberMap.put(member.getId(), member);
	}

	@Override
	public Member findById(Long memberId) {
		return memberMap.get(memberId);
	}

}
