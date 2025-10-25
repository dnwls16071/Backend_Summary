package com.jwj.springPrinciple.service;

import com.jwj.springPrinciple.entity.Member;

public interface MemberService {

	void join(Member member);
	Member findMember(Long memberId);
}
