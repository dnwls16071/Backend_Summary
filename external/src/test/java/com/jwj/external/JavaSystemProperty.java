package com.jwj.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class JavaSystemProperty {

	public static void main(String[] args) {
		Properties properties = System.getProperties();
		for (Object o : properties.keySet()) {
			log.info("prop {}={}", o, System.getProperty(String.valueOf(o)));
		}

		String url = System.getProperty("url");
		String username = System.getProperty("username");
		String password = System.getProperty("password");

		log.info("url={}", url);
		log.info("username={}", username);
		log.info("password={}", password);
	}
}
