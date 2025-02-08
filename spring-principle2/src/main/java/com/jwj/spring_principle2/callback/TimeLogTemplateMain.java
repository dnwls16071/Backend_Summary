package com.jwj.spring_principle2.callback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplateMain {
	public static void main(String[] args) {

		TimeLogTemplate timeLogTemplate = new TimeLogTemplate();
		timeLogTemplate.execute(new Callback() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});
	}
}
