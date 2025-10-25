package com.jwj.proxy.v1_proxy;

public interface LogTrace {
	TraceStatus begin(String message);
	void end(TraceStatus status);
	void exception(TraceStatus status, Exception e);
}
