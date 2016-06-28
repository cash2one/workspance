/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-17
 */
package com.ast.ast1949.web;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-17
 */
public class BaseWebControllerTest extends AbstractTransactionalSpringContextTests {
	
	protected Connection connection;
	@Autowired
	private SqlMapClientFactoryBean sqlMapClient;
	
	protected String[] getConfigLocations(){
		return new String[] {"spring-persist-test.xml","spring-service.xml"};
	}
	
	public void onSetUp(){
		try{
			connection = ((SqlMapClient) sqlMapClient.getObject()).getDataSource().getConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTearDown(){
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
	
	@Test
	public void test_demo(){

	}


}
