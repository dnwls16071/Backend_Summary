package com.jwj.external.v3;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("my.datasource")
public class MyDatasourcePropertiesV3 {

	private String url;
	private String username;
	private String password;
	private Etc etc;

	public MyDatasourcePropertiesV3(String url, String username, String password, @DefaultValue Etc etc) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.etc = etc;
	}

	@Data
	public static class Etc {
		@Min(1) @Max(999)
		private int maxConnection;
		@DurationMin(seconds = 1) @DurationMax(seconds = 60)
		private Duration timeout;
		private List<String> options = new ArrayList<>();
	}
}
