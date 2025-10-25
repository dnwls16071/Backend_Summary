package com.example.younghanapi2;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Younghanapi2Application {

	public static void main(String[] args) {
		SpringApplication.run(Younghanapi2Application.class, args);
	}

	// 프록시 강제 초기화
	@Bean
	public Hibernate5JakartaModule hibernate5Module() {
		return new Hibernate5JakartaModule();
	}
}
