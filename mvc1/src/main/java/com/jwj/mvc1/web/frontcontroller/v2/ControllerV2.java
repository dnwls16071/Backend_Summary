package com.jwj.mvc1.web.frontcontroller.v2;

import com.jwj.mvc1.web.frontcontroller.v2.view.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {

	// MyView 반환
	MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
