package com.jwj.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jwj.external.pay"})
public class ExternalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalApplication.class, args);
	}

}
