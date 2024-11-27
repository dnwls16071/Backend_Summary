package com.jwj.external.v3;

import com.jwj.external.datasource.MyDataSource;
import com.jwj.external.v1.MyDatasourcePropertiesV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableConfigurationProperties(MyDatasourcePropertiesV3.class)
public class MyDataSourceConfigV3 {

	private final MyDatasourcePropertiesV1 properties;

	public MyDataSourceConfigV3(MyDatasourcePropertiesV1 properties) {
		this.properties = properties;
	}

	@Bean
	public MyDataSource dataSource() {
		return new MyDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword(), properties.getEtc().getMaxConnection(), properties.getEtc().getTimeout(), properties.getEtc().getOptions());
	}
}
