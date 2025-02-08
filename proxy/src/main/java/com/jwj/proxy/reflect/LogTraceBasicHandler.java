package com.jwj.proxy.reflect;

import com.jwj.proxy.v1_proxy.LogTrace;
import com.jwj.proxy.v1_proxy.TraceStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceBasicHandler implements InvocationHandler {

	private final Object target;	// 프록시 객체
	private final LogTrace logTrace;

	public LogTraceBasicHandler(Object target, LogTrace logTrace) {
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		TraceStatus status = null;
		try {
			String message = method.getDeclaringClass().getSimpleName() + "."
					+ method.getName() + "()";
			status = logTrace.begin(message);
			//로직 호출
			Object result = method.invoke(target, args);
			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
