package com.jwj.proxy.postprocessor;

import com.jwj.proxy.advice.LogTraceAdvice;
import com.jwj.proxy.config.AppV1Config;
import com.jwj.proxy.config.AppV2Config;
import com.jwj.proxy.v1_proxy.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
//@Import({AppV1Config.class, AppV2Config.class})
@Import(BeanPostProcessorTest.BeanPostProcessorConfig.class)
public class BeanPostProcessorConfig {

	@Bean
	public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
		return new PackageLogTracePostProcessor("com.jwj.proxy", getAdvisor(logTrace));
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		//pointcut
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("request*", "order*", "save*");
		//advice
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		//advisor = pointcut + advice
		return new DefaultPointcutAdvisor(pointcut, advice);
	}
}
