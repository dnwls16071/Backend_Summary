package com.jwj.db1.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
public class MemberRepositoryV1 {

	private final DataSource dataSource;	// 의존관계 주입

	public MemberRepositoryV1(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private void close(Connection con, Statement stmt, ResultSet rs) {
		JdbcUtils.closeResultSet(rs);
		JdbcUtils.closeStatement(stmt);
		JdbcUtils.closeConnection(con);
	}

	private Connection getConnection() throws SQLException {
		Connection con = dataSource.getConnection();
		log.info("get connection={}, class={}", con, con.getClass());
		return con;
	}
}
