package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.InquiryTask;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.InquiryTaskDao;

/**
 *	author:kongsj
 *	date:2013-7-11
 */
@Component("inquiryTaskDao")
public class InquiryTaskDaoImpl extends BaseDaoSupport implements InquiryTaskDao{

	private final static String SQL_FIX = "inquiryTask";
	
	@Override
	public Integer insert(InquiryTask inquiryTask) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),inquiryTask);
	}

	@Override
	public InquiryTask query(Integer companyId, Integer targetId,
			String targetType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		map.put("targetType", targetType);
		return (InquiryTask) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryOne"), map);
	}

}
