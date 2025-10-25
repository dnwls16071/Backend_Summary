package com.jwj.proxy.advisor;

import com.jwj.proxy.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	@Test
	void advisorTest2() {

		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
		nameMatchMethodPointcut.setMappedName("save");
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, new TimeAdvice());
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

		proxy.save();
		proxy.find();
	}

	@Test
	void advisorTest3() {
		DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
		DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());

		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory1 = new ProxyFactory(target);
		proxyFactory1.addAdvisor(advisor2);
		proxyFactory1.addAdvisor(advisor1);
		ServiceInterface proxy = (ServiceInterface) proxyFactory1.getProxy();

		proxy.save();
	}

	@Slf4j
	static class Advice1 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice1 호출");
			return invocation.proceed();
		}
	}

	@Slf4j
	static class Advice2 implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advice2 호출");
			return invocation.proceed();
		}
	}
}
