package com.jwj.proxy.postprocessor;

import com.jwj.proxy.advice.LogTraceAdvice;
import com.jwj.proxy.v1_proxy.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoProxyConfig {

	@Bean
	public Advisor advisor2(LogTrace logTrace) {
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression("execution(* com.jwj.proxy..*((..))");
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(aspectJExpressionPointcut, logTraceAdvice);
	}

	@Bean
	public Advisor advisor3(LogTrace logTrace) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* com.jwj.proxy..*(..)) && !execution(* com.jwj.proxy..noLog(..))");
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
	}
}
