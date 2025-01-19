package com.jwj.springPrinciple.repository;

import com.jwj.springPrinciple.entity.Member;

public interface MemberRepository {

	void save(Member member);
	Member findById(Long memberId);
}
