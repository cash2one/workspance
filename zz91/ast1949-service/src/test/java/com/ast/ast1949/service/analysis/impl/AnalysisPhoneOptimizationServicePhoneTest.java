package com.ast.ast1949.service.analysis.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.analysis.AnalysisPhoneOptimizationService;

public class AnalysisPhoneOptimizationServicePhoneTest extends
		BaseServiceTestCase {
	@Autowired
	private AnalysisPhoneOptimizationService analysisPhoneOptimizationService;

	@Test
	public void test_selectByAnalysisPhone() throws Exception {
		PageDto<AnalysisPhoneOptimizationDto> page = new PageDto<AnalysisPhoneOptimizationDto>();
		AnalysisSerchDto analysisSerchDto = new AnalysisSerchDto();
		analysisSerchDto.setId(0);

		PageDto<AnalysisPhoneOptimizationDto> dto = analysisPhoneOptimizationService
				.selectByAnalysisPhone(page, analysisSerchDto);
		Integer count=dto.getTotalRecords();
		Integer conut2=test(0);
		assertEquals(count, conut2);
	}


	public Integer test(Integer id) throws Exception {
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(
					"select count(0) from analysis_phone_optimization where phone_log_id="+id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer bb=null;
		while(rs.next()){
			bb=rs.getInt(1);
		}
		return bb;
	}
}
