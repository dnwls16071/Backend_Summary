package com.jwj.mvc1.web.frontcontroller.v3.impl;

import com.jwj.mvc1.web.frontcontroller.v3.ControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public ModelView process(Map<String, String> objects) {
		List<Member> all = memberRepository.findAll();
		ModelView members = new ModelView("members");
		members.getObjects().put("members", all);
		return members;
	}
}
