package com.jwj.external.v1;

import com.jwj.external.datasource.MyDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableConfigurationProperties(MyDatasourcePropertiesV1.class)
public class MyDataSourceConfigV1 {

	private final MyDatasourcePropertiesV1 properties;

	public MyDataSourceConfigV1(MyDatasourcePropertiesV1 properties) {
		this.properties = properties;
	}

	@Bean
	public MyDataSource dataSource() {
		return new MyDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword(), properties.getEtc().getMaxConnection(), properties.getEtc().getTimeout(), properties.getEtc().getOptions());
	}
}
