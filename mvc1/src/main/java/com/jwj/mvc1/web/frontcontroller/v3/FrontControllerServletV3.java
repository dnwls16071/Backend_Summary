package com.jwj.mvc1.web.frontcontroller.v3;

import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberFormControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberListControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.impl.MemberSaveControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

	private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

	public FrontControllerServletV3() {
		controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String requestURI = req.getRequestURI();

		ControllerV3 controllerV3 = controllerV3Map.get(requestURI);
		if (controllerV3 == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> paramMap = new HashMap<>();
		req.getParameterNames().asIterator().forEachRemaining(param -> paramMap.put(param, req.getParameter(param)));

		ModelView mv = controllerV3.process(paramMap);
		String viewName = mv.getViewName();	// 논리 이름
		MyView myView = viewResolver(viewName);
		myView.rendering(mv.getObjects(), req, resp);
	}

	// 물리 이름
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
