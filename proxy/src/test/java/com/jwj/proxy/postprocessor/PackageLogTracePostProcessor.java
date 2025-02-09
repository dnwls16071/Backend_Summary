package com.jwj.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

	private final String basePackage;
	private final Advisor advisor;

	public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
		this.basePackage = basePackage;
		this.advisor = advisor;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		log.info("param beanName={}, bean={}", beanName, bean.getClass());
		String packageName = bean.getClass().getPackageName();

		if (!packageName.startsWith(basePackage)) {
			return bean;
		}

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.addAdvisor(advisor);
		Object proxy = proxyFactory.getProxy();
		return proxy;
	}
}
