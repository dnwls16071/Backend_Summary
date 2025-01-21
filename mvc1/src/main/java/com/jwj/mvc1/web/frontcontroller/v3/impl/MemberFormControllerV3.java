package com.jwj.mvc1.web.frontcontroller.v3.impl;

import com.jwj.mvc1.web.frontcontroller.v3.ControllerV3;
import com.jwj.mvc1.web.frontcontroller.v3.view.ModelView;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

	@Override
	public ModelView process(Map<String, String> objects) {
		return new ModelView("new-form");
	}
}
