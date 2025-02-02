package com.jwj.db2_start;

import com.jwj.db2_start.jdbctemplate.JdbcTemplateItemRepositoryV1;
import com.jwj.db2_start.jdbctemplate.JdbcTemplateItemRepositoryV2;
import com.jwj.db2_start.jdbctemplate.JdbcTemplateItemRepositoryV3;
import com.jwj.db2_start.repository.ItemRepository;
import com.jwj.db2_start.service.ItemService;
import com.jwj.db2_start.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Import(Db2StartApplication.JdbcTemplateV3Config.class)
@SpringBootApplication
public class Db2StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(Db2StartApplication.class, args);
	}

	//@Configuration
	@RequiredArgsConstructor
	static class JdbcTemplateV1Config {

		private final DataSource dataSource;

		//@Bean
		public ItemService itemService() {
			return new ItemServiceV1(itemRepository());
		}

		//@Bean
		public ItemRepository itemRepository() {
			return new JdbcTemplateItemRepositoryV1(dataSource);
		}
	}

	//@Configuration
	@RequiredArgsConstructor
	static class JdbcTemplateV2Config {

		private final DataSource dataSource;

		//@Bean
		public ItemService itemService() {
			return new ItemServiceV1(itemRepository());
		}

		//@Bean
		public ItemRepository itemRepository() {
			return new JdbcTemplateItemRepositoryV2(dataSource);
		}
	}

	//@Configuration
	@RequiredArgsConstructor
	static class JdbcTemplateV3Config {

		private final DataSource dataSource;

		@Bean
		public ItemService itemService() {
			return new ItemServiceV1(itemRepository());
		}

		@Bean
		public ItemRepository itemRepository() {
			return new JdbcTemplateItemRepositoryV3(dataSource);
		}
	}
}
