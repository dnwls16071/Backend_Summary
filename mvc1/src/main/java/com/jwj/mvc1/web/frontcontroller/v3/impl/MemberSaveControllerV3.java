package com.jwj.mvc1.web.frontcontroller.v3.impl;

import com.jwj.mvc1.web.frontcontroller.v3.ControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public ModelView process(Map<String, String> paramMap) {

		String username = paramMap.get("username");
		int age = Integer.parseInt(paramMap.get("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		ModelView mv = new ModelView("save-result");
		mv.getObjects().put("member", member);
		return mv;
	}
}
