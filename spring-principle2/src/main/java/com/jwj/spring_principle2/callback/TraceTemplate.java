package com.jwj.spring_principle2.callback;

import com.jwj.spring_principle2.util.LogTrace;
import com.jwj.spring_principle2.util.TraceStatus;
import org.springframework.stereotype.Component;

@Component
public class TraceTemplate {

	private final LogTrace trace;

	public TraceTemplate(LogTrace trace) {
		this.trace = trace;
	}

	public <T> T execute(String message, TraceCallback<T> callback) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			//로직 호출
			T result = callback.call();
			trace.end(status);
			return result;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
