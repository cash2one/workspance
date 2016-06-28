package com.ast.ast1949.service.analysis.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ast.ast1949.domain.analysis.AnalysisCompPrice;
import com.ast.ast1949.domain.analysis.AnalysisInquiry;
import com.ast.ast1949.domain.analysis.AnalysisProduct;
import com.ast.ast1949.domain.analysis.AnalysisRegister;
import com.ast.ast1949.domain.analysis.AnalysisTradeKeywords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.analysis.AnalysisService;
import com.ast.ast1949.util.DateUtil;

public class AnalysisServiceImplTest extends BaseServiceTestCase {
	
	@Resource
	private AnalysisService analysisService;
	
	public void test_pageInquiry() throws Exception{
		cleanInquiry();
		initInquiry("0",123,5,"2011-12-05");
		String inquiryType = "0";
		Date gmtTarget = DateUtil.getDate("2011-12-05", "yyyy-MM-dd");
		PageDto<AnalysisInquiry> page = new PageDto<AnalysisInquiry>();
		page = analysisService.pageInquiry(inquiryType, gmtTarget, page);
		assertTrue("error",page.getRecords().size()>0);
	}

	public void test_queryProduct() throws Exception{
		cleanProduct();
		initProduct("1", "2", 3, "2012-12-2");
		initProduct("1", "2", 6, "2012-12-3");
		initProduct("7", "8", 9, "2012-12-4");
		initProduct("2", "3", 4, "2012-12-3");
		List<AnalysisProduct> list = analysisService.queryProduct("1", "2", DateUtil.getDate("2012-12-02", "yyyy-MM-dd"), DateUtil.getDate("2012-12-03", "yyyy-MM-dd"));
		assertTrue(list.size()>0);
	}

	public void test_queryCompPrice() throws Exception{
		cleanCompPrice();
		initCompPrice("1", 2, "2012-12-2");
		initCompPrice("1", 4, "2012-12-3");
		initCompPrice("3", 4, "2012-12-4");
		List<AnalysisCompPrice> list = analysisService.queryCompPrice("1", DateUtil.getDate("2012-12-02", "yyyy-MM-dd"), DateUtil.getDate("2012-12-03", "yyyy-MM-dd"));
		assertTrue(list.size()>0);
	}
	
	public void test_queryKeywords() throws Exception{
		cleanKeywords();
		initKeywords("pe", "1002", 2, "2012-12-4");
		PageDto<AnalysisTradeKeywords> page = new PageDto<AnalysisTradeKeywords>();
		page = analysisService.pageKeywords(DateUtil.getDate("2012-12-4", "yyyy-MM-dd"), page);
		assertTrue("error",page.getRecords().size()>0);
	}
	
	public void test_queryRegister() throws Exception{
		cleanRegister();
		initRegister("1000", "1002", 2, "2012-12-2");
		initRegister("1000", "1002", 2, "2012-12-4");
		List<AnalysisRegister> list = analysisService.queryRegister("1000", DateUtil.getDate("2012-12-02", "yyyy-MM-dd"), DateUtil.getDate("2012-12-03", "yyyy-MM-dd"));
		assertTrue("error",list.size()>0);
	}

	private void cleanInquiry() throws Exception{
		connection.prepareStatement("delete from analysis_inquiry").execute();
	}
	
	private void initInquiry(String inquiryType,Integer inquiryTarget,Integer num,String gmtTarget){
		try {
			connection.prepareStatement("insert into analysis_inquiry (inquiry_type,inquiry_target,num,gmt_target,gmt_created,gmt_modified) values('"+
					inquiryType+"',"+inquiryTarget+","+num+",'"+gmtTarget+"',now(),now())").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cleanProduct() throws Exception{
		connection.prepareStatement("delete from analysis_product").execute();
	}
	
	private void initProduct(String typeCode,String categoryCode,Integer num,String gmtTarget){
		try {
			connection.prepareStatement("insert into analysis_product (type_code, category_code, num, gmt_target, gmt_created, gmt_modified) values('"+
					typeCode+"',"+categoryCode+","+num+",'"+gmtTarget+"',now(),now())").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cleanCompPrice() throws Exception{
		connection.prepareStatement("delete from analysis_comp_price").execute();
	}
	
	private void initCompPrice(String categoryCode,Integer num,String gmtTarget){
		try {
			connection.prepareStatement("insert into analysis_comp_price (category_code, num, gmt_target, gmt_created, gmt_modified) values('"
					+categoryCode+"',"+num+",'"+gmtTarget+"',now(),now())").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cleanRegister() throws Exception{
		connection.prepareStatement("delete from analysis_register").execute();
	}
	
	private void initRegister(String regfromCode,String categoryCode,Integer num,String gmtTarget){
		try {
			connection.prepareStatement("insert into analysis_register (regfrom_code, num, gmt_target, gmt_created, gmt_modified) values('"+
					regfromCode+"',"+num+",'"+gmtTarget+"',now(),now())").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cleanKeywords() throws Exception{
		connection.prepareStatement("delete from analysis_trade_keywords").execute();
	}
	
	private void initKeywords(String kw,String categoryCode,Integer num,String gmtTarget){
		try {
			connection.prepareStatement("insert into analysis_trade_keywords (kw, num, gmt_target, gmt_created, gmt_modified) values('"+
					kw+"',"+num+",'"+gmtTarget+"',now(),now())").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
