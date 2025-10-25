package com.jwj.mvc1.web.frontcontroller.v3.view;

import java.util.HashMap;
import java.util.Map;

public class ModelView {

	private String viewName;
	private Map<String, Object> objects = new HashMap<>();

	public ModelView(String viewName) {
        this.viewName = viewName;
    }

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Map<String, Object> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, Object> objects) {
		this.objects = objects;
	}
}
