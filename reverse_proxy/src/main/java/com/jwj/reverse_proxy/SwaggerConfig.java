package com.jwj.reverse_proxy;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 기본 URL : http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API 문서")
						.description("API 명세서입니다.")
						.version("v1.0.0"));
	}
}

