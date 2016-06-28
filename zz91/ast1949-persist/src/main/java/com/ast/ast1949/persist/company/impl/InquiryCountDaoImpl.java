/**
 * @author kongsj
 * @date 2014年5月30日
 * 
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.InquiryCountDao;

@Component("inquiryCountDao")
public class InquiryCountDaoImpl extends BaseDaoSupport implements InquiryCountDao {

	final static String SQL_FIX = "inquiryCount";
	
	@Override
	public Integer queryByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryCompany(PageDto<CompanyDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCompany"), map);
	}
	@Override
	public Integer queryCompanyCount(){
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCompanyCount"));
	}
}
