package com.jwj.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DbConfig {

	@Bean
	public DataSource dataSource() {
		log.info("dataSource 빈 등록");
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName("org.h2.Driver");
		hikariDataSource.setJdbcUrl("jdbc:h2:mem:test");
		hikariDataSource.setUsername("sa");
		hikariDataSource.setPassword("");
		return hikariDataSource;
	}

	// 트랜잭션 매니저 등록
	@Bean
	public TransactionManager transactionManager() {
		log.info("transactionManger 빈 등록");
		return new JdbcTransactionManager(dataSource());
	}

	// JdbcTemplate 등록
	@Bean
	public JdbcTemplate jdbcTemplate() {
		log.info("jdbcTemplate 빈 등록");
		return new JdbcTemplate(dataSource());
	}
}
