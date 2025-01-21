package com.jwj.mvc1.web.frontcontroller.v3;

import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;

import java.util.Map;

public interface ControllerV3 {

	// ModelView 반환
	ModelView process(Map<String, String> objects);
}
