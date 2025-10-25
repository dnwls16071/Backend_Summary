package com.jwj.mvc1.web.frontcontroller.v1;

import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberFormControllerV1;
import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberListControllerV1;
import com.jwj.mvc1.web.frontcontroller.v1.impl.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

	private Map<String, ControllerV1> controllerV1Map = new HashMap<>();

	public FrontControllerServletV1() {
		controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
		controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
		controllerV1Map.put("/front-controller/v1/members", new MemberListControllerV1());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String requestURI = req.getRequestURI();
		System.out.println(requestURI);
		ControllerV1 controllerV1 = controllerV1Map.get(requestURI);
		if (controllerV1 == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		controllerV1.process(req, resp);
	}
}
