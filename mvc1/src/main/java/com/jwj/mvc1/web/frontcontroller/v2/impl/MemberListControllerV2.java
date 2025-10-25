package com.jwj.mvc1.web.frontcontroller.v2.impl;

import com.jwj.mvc1.web.frontcontroller.v2.ControllerV2;
import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class MemberListControllerV2 implements ControllerV2 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Member> members = memberRepository.findAll();
		request.setAttribute("members", members);
		return new MyView("/WEB-INF/views/members.jsp");
	}
}
