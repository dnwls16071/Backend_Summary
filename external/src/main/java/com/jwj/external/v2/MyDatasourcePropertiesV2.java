package com.jwj.external.v2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("my.datasource")
public class MyDatasourcePropertiesV2 {

	private String url;
	private String username;
	private String password;
	private Etc etc;

	public MyDatasourcePropertiesV2(String url, String username, String password, @DefaultValue Etc etc) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.etc = etc;
	}

	@Data
	public static class Etc {
		private int maxConnection;
		private Duration timeout;
		private List<String> options = new ArrayList<>();
	}
}
