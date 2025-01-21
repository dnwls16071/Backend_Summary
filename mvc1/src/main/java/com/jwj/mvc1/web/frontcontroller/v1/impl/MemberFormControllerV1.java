package com.jwj.mvc1.web.frontcontroller.v1.impl;

import com.jwj.mvc1.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String viewPath = "/WEB-INF/views/new-form.jsp";
        request.getRequestDispatcher(viewPath).forward(request, response);
	}
}
