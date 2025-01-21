package com.jwj.mvc1.web.frontcontroller.v1.impl;

import com.jwj.mvc1.web.frontcontroller.v1.ControllerV1;
import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);
		request.setAttribute("member", member);
		String viewPath = "/WEB-INF/views/save-result.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
	}
}
