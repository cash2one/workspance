/**
 * @author shiqp
 * @date 2016-01-11
 */
package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.CompanyInfoDao;

@Component("companyInfoDao")
public class CompanyInfoDaoImpl extends BaseDaoSupport implements CompanyInfoDao {
	final static String SQL_PREFIX = "companyInfo";

	@Override
	public Integer insertCompanyInfo(CompanyInfo companyInfo) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertCompanyInfo"), companyInfo);
	}

	@Override
	public CompanyInfo queryById(Integer id) {
		return (CompanyInfo) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}
	
	@Override
	public CompanyInfo queryWithoutCheckInfoById(Integer id) {
		return (CompanyInfo) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryWithoutCheckInfoById"), id);
	}

	@Override
	public Integer updateValidate(CompanyInfo companyInfo) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateValidate"),companyInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyInfo> queryCompanyList(PageDto<CompanyDto> page, CompanySearch search) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("search", search);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInfoByCondition"), map);
	}

	@Override
	public Integer countCompanyList(CompanySearch search) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countinfoByCondition"), map);
	}
	
	@Override
	public Integer updateStatus(Integer valueOf, Integer checkStatus){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", valueOf);
		map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatus"),map);
	}
	
	@Override
	public Integer updateDelStatus(Integer valueOf, Integer checkStatus){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", valueOf);
		map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateDelStatus"),map);
	}
	
	@Override
	public Integer updateCompanyByAdmin(CompanyInfo companyInfo){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByAdmin"),companyInfo);
	}
}
