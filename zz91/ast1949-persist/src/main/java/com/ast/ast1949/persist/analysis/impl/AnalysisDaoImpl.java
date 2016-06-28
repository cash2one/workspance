/**
 * 
 */
package com.ast.ast1949.persist.analysis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisCompPrice;
import com.ast.ast1949.domain.analysis.AnalysisInquiry;
import com.ast.ast1949.domain.analysis.AnalysisLog;
import com.ast.ast1949.domain.analysis.AnalysisProduct;
import com.ast.ast1949.domain.analysis.AnalysisRegister;
import com.ast.ast1949.domain.analysis.AnalysisTradeKeywords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisSpotDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisDao;

/**
 * @author root
 * 
 */
@SuppressWarnings("unchecked")
@Component("analysisDao")
public class AnalysisDaoImpl extends BaseDaoSupport implements AnalysisDao {

	final static String SQL_PREFIX = "analysis";

	@Override
	public Integer queryEsiteVisit(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryEsiteVisit"), companyId);
	}

	@Override
	public List<AnalysisInquiry> queryInquiry(String inquiryType,
			Date gmtTarget, PageDto<AnalysisInquiry> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inquiryType", inquiryType);
		map.put("gmtTarget",gmtTarget);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryInquiry"), map);
	}

	@Override
	public Integer queryInquiryCount(String inquiryType,Date gmtTarget) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inquiryType", inquiryType);
		map.put("gmtTarget",gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryInquiryCount"), map);
	}

	@Override
	public List<AnalysisTradeKeywords> queryKeywords(Date gmtTarget,
			PageDto<AnalysisTradeKeywords> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gmtTarget", gmtTarget);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryKeywords"), map);
	}

	@Override
	public Integer queryKeywordsCount(Date gmtTarget) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gmtTarget", gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryKeywordsCount"), map);
	}

	@Override
	public List<AnalysisCompPrice> queryCompPrice(String categoryCode,
			Date start, Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryCode", categoryCode);
		map.put("start", start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompPrice"),map);
	}

	@Override
	public List<AnalysisInquiry> queryInquiryRang(String inquiryType,
			Integer inquiryTarget, Date start, Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("inquiryType", inquiryType);
		map.put("inquiryTarget", inquiryTarget);
		map.put("start", start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryRang"), map);
	}

	@Override
	public List<AnalysisTradeKeywords> queryKeywordsRang(String kw, Date start,
			Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("kw", kw);
		map.put("start",start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryKeywordsRang"), map);
	}

	@Override
	public List<AnalysisProduct> queryProduct(String typeCode,
			String categoryCode, Date start, Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeCode", typeCode);
		map.put("categoryCode", categoryCode);
		map.put("start", start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProduct"), map);
	}

	@Override
	public List<AnalysisRegister> queryRegister(String regfromCode, Date start,
			Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("regfromCode", regfromCode);
		map.put("start",start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryRegister"), map);
	}

	@Override
	public Integer summaryInquiry(String inquiryType, Date gmtTarget) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inquiryType", inquiryType);
		map.put("gmtTarget",gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "summaryInquiry"), map);
	}

	@Override
	public Integer summaryKeywords(Date gmtTarget) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gmtTarget", gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "summaryKeywords"), map);
	}
	
	@Override
	public List<AnalysisLog> queryAnalysisLog(String operator,String operation,Date start,Date end){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("operator", operator);
		map.put("operation", operation);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAnalysisLog"),map);
	}

	@Override
	public List<AnalysisSpotDto> queryAnalysisLogForSpot(PageDto<AnalysisSpotDto> page,String operator,
			String operation, Date start, Date end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("operator", operator);
		map.put("operation", operation);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAnalysisLogForSpot"),map);
	}

	@Override
	public List<AnalysisLog> querySpot(String operator, Date start, Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operator", operator);
		map.put("start",start);
		map.put("end", end);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySpot"), map);
	}

	@Override
	public Integer countQueryAnalysisLogForSpot(String operator,
			String operation, Date start, Date end) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operator", operator);
		map.put("start",start);
		map.put("end", end);
		map.put("operation", operation);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countQueryAnalysisLogForSpot"), map);
	}

	@Override
	public Integer summarySpot(Date start, Date end,String operation) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start",start);
		map.put("end", end);
		map.put("operation", operation);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "summarySpot"), map);
	}

}
