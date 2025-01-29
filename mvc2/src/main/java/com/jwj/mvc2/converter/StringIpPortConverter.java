package com.jwj.mvc2.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringIpPortConverter implements Converter<String, IpPort> {

	@Override
	public IpPort convert(String source) {
		String[] split = source.split(":");
		String ip = split[0];
		int port = Integer.parseInt(split[1]);
		return new IpPort(ip, port);
	}
}
