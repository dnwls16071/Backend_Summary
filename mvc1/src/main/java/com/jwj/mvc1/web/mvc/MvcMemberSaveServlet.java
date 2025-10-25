package com.jwj.mvc1.web.mvc;

import com.jwj.mvc1.web.member.Member;
import com.jwj.mvc1.web.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));

		Member member = new Member(username, age);
		memberRepository.save(member);

		String viewPath = "/WEB-INF/views/save-result.jsp";
		req.getRequestDispatcher(viewPath).forward(req, resp);
	}
}
