package com.jwj.mvc1.web.frontcontroller.v5.adapter;

import com.jwj.mvc1.web.frontcontroller.v3.ControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import com.jwj.mvc1.web.frontcontroller.v4.ControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return (handler instanceof ControllerV3);	// ControllerV4를 지원하는가?
	}

	@Override
	public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
		ControllerV4 controller = (ControllerV4) handler;
		Map<String, Object> map = new HashMap<>();

		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>();

		String viewName = controller.process(paramMap, model);
		ModelView modelView = new ModelView(viewName);
		modelView.setObjects(model);
		return modelView;
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator().forEachRemaining(param -> paramMap.put(param, request.getParameter(param)));
		return paramMap;
	}
}
