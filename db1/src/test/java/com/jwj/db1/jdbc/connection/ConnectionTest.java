package com.jwj.db1.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jwj.db1.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

	@Test
	@DisplayName("DriverManager")
	void driverManager() throws SQLException {

		// 커넥션 획득시마다 필요한 파라미터를 넘겨야 한다.
		Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		//19:38:27.489 [Test worker] INFO com.jwj.db1.jdbc.connection.ConnectionTest -- connection=conn0: url=jdbc:h2:tcp://localhost/~/db1 user=SA, class=class org.h2.jdbc.JdbcConnection
		//19:38:27.493 [Test worker] INFO com.jwj.db1.jdbc.connection.ConnectionTest -- connection=conn1: url=jdbc:h2:tcp://localhost/~/db1 user=SA, class=class org.h2.jdbc.JdbcConnection
		log.info("connection={}, class={}", con1, con1.getClass());
		log.info("connection={}, class={}", con2, con2.getClass());
	}

	@Test
	@DisplayName("DriverManagerDataSource")
	void dataSourceManagerDataSource() throws SQLException {

		// 초기 세팅시 설정값을 넘긴다.
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

		//19:40:26.793 [Test worker] INFO com.jwj.db1.jdbc.connection.ConnectionTest -- connection=conn0: url=jdbc:h2:tcp://localhost/~/db1 user=SA, class=class org.h2.jdbc.JdbcConnection
		//19:40:26.797 [Test worker] INFO com.jwj.db1.jdbc.connection.ConnectionTest -- connection=conn1: url=jdbc:h2:tcp://localhost/~/db1 user=SA, class=class org.h2.jdbc.JdbcConnection
		useDataSource(dataSource);
	}

	private void useDataSource(DataSource dataSource) throws SQLException {
		Connection con1 = dataSource.getConnection();
		Connection con2 = dataSource.getConnection();
		log.info("connection={}, class={}", con1, con1.getClass());
		log.info("connection={}, class={}", con2, con2.getClass());
	}

	@Test
	@DisplayName("HikariDataSource")
	void dataSourceConnectionPool() throws SQLException, InterruptedException {

		// DataSource 인터페이스에 의존적으로 개발을 하지만 추가적인 설정을 위해 HikariDataSource로 받는다.
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setJdbcUrl(URL);
		hikariDataSource.setUsername(USERNAME);
		hikariDataSource.setPassword(PASSWORD);
		hikariDataSource.setMaximumPoolSize(10);
		hikariDataSource.setPoolName("MyPool");

		useDataSource(hikariDataSource);
		Thread.sleep(1000);
	}
}
