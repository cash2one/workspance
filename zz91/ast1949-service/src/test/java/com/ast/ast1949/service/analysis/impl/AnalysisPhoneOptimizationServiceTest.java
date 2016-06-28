package com.ast.ast1949.service.analysis.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.analysis.AnalysisPhoneOptimizationService;
import com.ast.ast1949.util.DateUtil;

public class AnalysisPhoneOptimizationServiceTest extends BaseServiceTestCase {
	@Autowired
	private AnalysisPhoneOptimizationService analysisPhoneOptimizationService;
    @Test
	public void test_createOneRecord() throws Exception {
		SimpleDateFormat fors = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		System.out.println(fors.format(new Date()));
		clean();

		String date = fors.format(DateUtil.addDays(new Date(), -1));
		insert(date);
		AnalysisPhoneOptimization analysisPhoneOptimization = new AnalysisPhoneOptimization();
		analysisPhoneOptimization.setIp("193.168.2.178");
		analysisPhoneOptimization.setUtmSource("0");
		analysisPhoneOptimization.setUtmTerm("0");
		analysisPhoneOptimizationService.createOneRecord(analysisPhoneOptimization);
		analysisPhoneOptimizationService.createOneRecord(analysisPhoneOptimization);


		AnalysisPhoneOptimization tt = selectAll(fors.format(new Date()));
		assertEquals(0, tt.getPhoneLogId().intValue());
		assertEquals("193.168.2.178", tt.getIp());
		assertEquals(0, tt.getIsValid().intValue());
		assertEquals(1, tt.getIsFirst().intValue());

		// 断言is_first是否第一次出现
		int isfirst = queryisfirst("193.168.2.178");
		assertEquals(1, isfirst);
				
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from analysis_phone_optimization").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insert(String date) {
		SimpleDateFormat fors = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			connection.prepareStatement("insert into analysis_phone_optimization values('" + 0
					+ "','0','193.168.2.178','0','0','0','0','0','0','0','0','0','" + date + "','"
					+ fors.format(new Date()) + "','" + fors.format(new Date()) + "')").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Integer queryisfirst(String ip) throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			rs = connection.createStatement().executeQuery("select is_first from analysis_phone_optimization where ip='"
					+ ip + "'" + " order by gmt_target desc");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Test
	private Integer queryOne(String ip, String date) throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			rs = connection.createStatement().executeQuery("select count(0) from analysis_phone_optimization where ip='"
					+ ip + "'" + "and gmt_target='" + date + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Test
	private Integer queryip(String ip) throws Exception {
		ResultSet rs = null;
		int count = 0;
		try {
			rs = connection.createStatement()
					.executeQuery("select count(0) from analysis_phone_optimization where ip='" + ip + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	
	
	@Test
	private AnalysisPhoneOptimization selectAll(String date) throws Exception {
		ResultSet rs = null;
		AnalysisPhoneOptimization analysisPhoneOptimization = new AnalysisPhoneOptimization();
		try {
			rs = connection.createStatement()
					.executeQuery("select * from analysis_phone_optimization where gmt_target='"+date+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			analysisPhoneOptimization.setId(rs.getInt(1));
			analysisPhoneOptimization.setPhoneLogId(rs.getInt(2));
			analysisPhoneOptimization.setIp(rs.getString(3));
			analysisPhoneOptimization.setUtmSource(rs.getString(4));
			analysisPhoneOptimization.setUtmTerm(rs.getString(5));
			analysisPhoneOptimization.setUtmContent(rs.getString(6));
			analysisPhoneOptimization.setUtmCampaign(rs.getString(7));
			analysisPhoneOptimization.setIsValid(rs.getInt(8));
			analysisPhoneOptimization.setIsFirst(rs.getInt(9));
			analysisPhoneOptimization.setPageFirst(rs.getString(10));
			analysisPhoneOptimization.setPageLast(rs.getString(11));
			analysisPhoneOptimization.setPageCalling(rs.getString(12));
			analysisPhoneOptimization.setGmtTarget(rs.getString(13));
			analysisPhoneOptimization.setGmtCreated(rs.getDate(14));
			analysisPhoneOptimization.setGmtModified(rs.getDate(15));
		}
		return analysisPhoneOptimization;
	}

}
