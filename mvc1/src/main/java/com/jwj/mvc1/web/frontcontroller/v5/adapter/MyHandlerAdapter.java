package com.jwj.mvc1.web.frontcontroller.v5.adapter;

import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

	boolean supports(Object handler);
	ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
