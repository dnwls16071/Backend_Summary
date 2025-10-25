package com.jwj.spring_principle2.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

	// ContextV1과 달리 파라미터로 넘겨 동시성 문제를 차단한다.
	public void execute(Strategy strategy) {
		long startTime = System.currentTimeMillis();
		//비즈니스 로직 실행
		strategy.call(); //위임
		//비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime={}", resultTime);
	}
}
