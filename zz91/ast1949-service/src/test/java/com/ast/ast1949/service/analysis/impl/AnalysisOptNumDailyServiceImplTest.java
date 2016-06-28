/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.service.analysis.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.analysis.AnalysisOptNumDaily;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.analysis.AnalysisOptNumDailyService;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
public class AnalysisOptNumDailyServiceImplTest extends BaseServiceTestCase{

	@Autowired
	AnalysisOptNumDailyService analysisOptNumDailyService;
	
	final static int NUM = 5;
	
	@Test
	public void test_insertOptNum_insert(){
		clean();
		analysisOptNumDailyService.insertOptNum("test-opt", "test", 1);
		AnalysisOptNumDaily o = queryOptNum("test-opt", "test");
		assertNotNull(o);
		assertEquals(1, o.getOptNum().intValue());
	}
	
	@Test
	public void test_insertOptNum_update(){
		clean();
		prepareData("test-opt", "test", 1, 10);
		analysisOptNumDailyService.insertOptNum("test-opt", "test", 1);
		
		AnalysisOptNumDaily o = queryOptNum("test-opt", "test");
		assertNotNull(o);
		assertEquals(11, o.getOptNum().intValue());
	}
	@Test
	public void test_queryOptNumByAccountToday(){
		clean();
		prepareData("test-opt","test", 1, NUM);
		Integer i= analysisOptNumDailyService.queryOptNumByAccountToday("test-opt", "test");
		assertNotNull(i);
		assertEquals(NUM, i.intValue());
	}
	
	@Test
	public void test_queryOptNumByAccountToday_noData(){
		clean();
		Integer i= analysisOptNumDailyService.queryOptNumByAccountToday("test-opt", "test");
		assertNotNull(i);
		assertEquals(0, i.intValue());
	}
	
	/************prepare data*********/
	private void clean() {
		try {
			connection.prepareStatement("delete from analysis_opt_num_daily").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	private void prepareData(String opt, String account, Integer companyId, Integer num){
		String sql="";
		sql="insert into analysis_opt_num_daily(category_code,account,company_id, opt_num, gmt_daily) " +
				"values('"+opt+"','"+account+"',"+companyId+","+num+",curdate())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private AnalysisOptNumDaily queryOptNum(String opt, String account){
		String sql = "";
		sql = "select * from analysis_opt_num_daily where account='"+account+"' and category_code='"+opt+"' and gmt_daily=curdate() ";
		ResultSet rs;
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new AnalysisOptNumDaily(rs.getInt("company_id"), 
						rs.getString("account"), 
						rs.getString("category_code"), 
						rs.getInt("opt_num"),
						rs.getDate("gmt_daily"), 
						rs.getDate("gmt_created"), 
						rs.getDate("gmt_modified"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
