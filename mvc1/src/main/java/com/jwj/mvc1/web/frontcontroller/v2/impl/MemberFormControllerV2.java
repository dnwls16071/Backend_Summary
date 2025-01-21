package com.jwj.mvc1.web.frontcontroller.v2.impl;

import com.jwj.mvc1.web.frontcontroller.v2.ControllerV2;
import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

	@Override
	public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		return new MyView("/WEB-INF/views/new-form.jsp");
	}
}
