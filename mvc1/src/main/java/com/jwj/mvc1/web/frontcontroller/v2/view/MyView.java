package com.jwj.mvc1.web.frontcontroller.v2.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {

	private String viewPath;

	public MyView(String viewPath) {
		this.viewPath = viewPath;
	}

	public void rendering(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
		requestDispatcher.forward(request, response);
	}

	public void rendering(Map<String, Object> model, HttpServletRequest request,
					   HttpServletResponse response) throws ServletException, IOException {
		modelToRequestAttribute(model, request);
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
	}

	private void modelToRequestAttribute(Map<String, Object> model,
										 HttpServletRequest request) {
		model.forEach((key, value) -> request.setAttribute(key, value));
	}
}
