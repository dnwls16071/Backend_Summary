package com.jwj.mvc1.web.frontcontroller.v4;

import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberFormControllerV4;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberListControllerV4;
import com.jwj.mvc1.web.frontcontroller.v4.impl.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

	private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

	public FrontControllerServletV4() {
		controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String requestURI = req.getRequestURI();

		ControllerV4 controllerV4 = controllerV4Map.get(requestURI);
		if (controllerV4 == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> paramMap = new HashMap<>();
		Map<String, Object> model = new HashMap<>();
		req.getParameterNames().asIterator().forEachRemaining(param -> paramMap.put(param, req.getParameter(param)));

		String viewName = controllerV4.process(paramMap, model);
		MyView myView = viewResolver(viewName);
		myView.rendering(model, req, resp);
	}

	// 물리 이름
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
