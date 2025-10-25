package com.jwj.spring_principle2.util;

public interface LogTrace {
	TraceStatus begin(String message);
	void end(TraceStatus status);
	void exception(TraceStatus status, Exception e);
}
