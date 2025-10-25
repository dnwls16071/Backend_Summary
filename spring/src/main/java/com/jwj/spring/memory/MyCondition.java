package com.jwj.spring.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class MyCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		// 메모리 사용량 조회 기능을 언제 활성화시킬거야?
		String memory = context.getEnvironment().getProperty("memory");
		log.info("memory={}", memory);
		return "on".equals(memory);
	}
}
