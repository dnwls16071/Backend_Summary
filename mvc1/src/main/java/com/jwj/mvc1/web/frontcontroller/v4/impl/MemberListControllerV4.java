package com.jwj.mvc1.web.frontcontroller.v4.impl;

import com.jwj.mvc1.web.frontcontroller.v4.ControllerV4;
import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public String process(Map<String, String> paramMap, Map<String, Object> model) {
		List<Member> members = memberRepository.findAll();
		model.put("members", members);
		return "members";
	}
}
