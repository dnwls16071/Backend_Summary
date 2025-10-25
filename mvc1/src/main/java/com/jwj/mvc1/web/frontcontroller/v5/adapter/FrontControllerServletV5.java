package com.jwj.mvc1.web.frontcontroller.v5.adapter;

import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberFormControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberListControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberSaveControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberFormControllerV4;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberListControllerV4;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	// 유연한 처리
	public FrontControllerServletV5() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberListControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());

		//V4 추가
		handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Object handler = getHandler(request);
		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView mv = adapter.handle(request, response, handler);
		MyView view = viewResolver(mv.getViewName());
		view.rendering(mv.getObjects(), request, response);
	}
	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMappingMap.get(requestURI);
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) {
		for (MyHandlerAdapter adapter : handlerAdapters) {
			if (adapter.supports(handler)) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
