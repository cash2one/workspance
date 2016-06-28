package com.zz91.ads.board.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseTestCase extends
		AbstractDependencyInjectionSpringContextTests {

	protected Connection connection;

	@Autowired
	private SqlMapClientFactoryBean sqlMapClient;

	protected String[] getConfigLocations() {
		return new String[] { "spring-ads-board.xml" };
	}

	public void test_demo() {
		
	}

	public void onSetUp() {
		try {
			DataSource dataSource = ((SqlMapClient) sqlMapClient.getObject()).getDataSource();
			connection = dataSource.getConnection();//((SqlMapClient) sqlMapClient.getObject()).getDataSource().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTearDown() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	/*
	 * 获取数据库中最后一条记录的ID值
	 */
	public int getLastInsertId() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"SELECT LAST_INSERT_ID()");
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
