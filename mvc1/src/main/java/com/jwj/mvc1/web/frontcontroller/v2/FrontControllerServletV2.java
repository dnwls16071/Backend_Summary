package com.jwj.mvc1.web.frontcontroller.v2;

import com.jwj.mvc1.web.frontcontroller.v1.ControllerV1;
import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberFormControllerV1;
import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberListControllerV1;
import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberSaveControllerV1;
import com.jwj.mvc1.web.frontcontroller.v2.impl.MemberFormControllerV2;
import com.jwj.mvc1.web.frontcontroller.v2.impl.MemberListControllerV2;
import com.jwj.mvc1.web.frontcontroller.v2.impl.MemberSaveControllerV2;
import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

	private Map<String, ControllerV2> controllerV2Map = new HashMap<>();

	public FrontControllerServletV2() {
		controllerV2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
		controllerV2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerV2Map.put("/front-controller/v2/members", new MemberListControllerV2());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String requestURI = req.getRequestURI();

		ControllerV2 controllerV2 = controllerV2Map.get(requestURI);
		if (controllerV2 == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MyView view = controllerV2.process(req, resp);
		view.rendering(req, resp);
	}
}
