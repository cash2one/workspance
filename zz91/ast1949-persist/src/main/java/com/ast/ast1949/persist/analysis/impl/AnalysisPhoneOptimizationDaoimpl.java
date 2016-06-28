package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisPhoneOptimization;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisPhoneOptimizationDao;


@Component("AnalysisPhoneOptimizationDao")
public class AnalysisPhoneOptimizationDaoimpl extends BaseDaoSupport implements AnalysisPhoneOptimizationDao{
	final static String SQL_PREFIX = "analysisphoneoptimization";

	@Override
	public Integer createOneRecord(
			AnalysisPhoneOptimization analysisPhoneOptimization) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "createOneRecord"), analysisPhoneOptimization);
	}

	@Override
	public Integer selectOneRecord(AnalysisPhoneOptimization analysisPhoneOptimization) {
		return  (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectOneRecord"), analysisPhoneOptimization);
	}

	@Override
	public Integer selectIp(AnalysisPhoneOptimization analysisPhoneOptimization) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectIp"),analysisPhoneOptimization)>0?0:1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisPhoneOptimization> selectByAnalysisPhone(
			AnalysisSerchDto analysisSerchDto,
			PageDto<AnalysisPhoneOptimizationDto> page) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("analysisSerchDto", analysisSerchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "selectByAnalysisPhone"),map);
	}

	@Override
	public PhoneLog selectSize(Integer id) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		return (PhoneLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectSize"),map);
	}

	@Override
	public Integer selectByAnalysisPhoneSzie(AnalysisSerchDto analysisSerchDto) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("analysisSerchDto", analysisSerchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectByAnalysisPhoneSzie"),map);
	}


}
