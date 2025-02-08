package com.jwj.spring_principle2.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1 {

	private final Strategy strategy;

	public ContextV1(Strategy strategy) {
		this.strategy = strategy;
	}

	// Context에서 call()을 호출할 때 Strategy에 위임한다.
	public void execute() {
		long startTime = System.currentTimeMillis();
		//비즈니스 로직 실행
		strategy.call(); //위임
		//비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime={}", resultTime);
	}
}
