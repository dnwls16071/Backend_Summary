package com.jwj.mvc1.web.frontcontroller.v5.adapter;

import com.jwj.mvc1.web.frontcontroller.v3.ControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV3);	// ControllerV3를 지원하는가?
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
		ControllerV3 controller = (ControllerV3) handler;
		return controller.process(createParamMap(request));
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator().forEachRemaining(param -> paramMap.put(param, request.getParameter(param)));
		return paramMap;
	}
}
