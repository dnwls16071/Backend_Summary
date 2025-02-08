package com.jwj.proxy.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecoratorPatternClient {

	private Component component;

	public DecoratorPatternClient(Component component) {
		this.component = component;
	}

	public void execute() {
		String operation = component.operation();
		log.info("operation={}", operation);
	}
}
