package com.kl91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CompanyDto;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.company.CompanyDao;

@Component("companyDao")
public class CompanyDaoImpl extends BaseDaoSupport implements CompanyDao {
	
	private static String SQL_PREFIX = "company";

	public Integer insert(Company company) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertCompany"), company);
	}

	public Integer update(Company company) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCompany"),company);
	}

	public Company queryById(Integer id) {
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}

	public String queryDomainById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryDomainById"), id);
	}

	public String queryIntroductionById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryIntroductionById"), id);
	}
	
	public Integer delete(Integer id) {
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteCompany"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompany(CompanyDto dto, PageDto<Company> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("dto", dto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompany"),root);
	}

	@Override
	public Integer updatePassNumById(Integer companyId, Integer count) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("count", count);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePassNumById"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryMostCompanyByNumPass(Integer size) {		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMostPublish"),size);
	}

	@Override
	public Company validateAccount(String account, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", account);
		map.put("pwd", pwd);
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateAccount"), map);
	}
	
	@Override
	public Company validateEmail(String email, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email", email);
		map.put("pwd", pwd);
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "validateEmail"), map);
	}

	@Override
	public Company queryByEmail(String email) {
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByEmail"), email);
	}

	@Override
	public Integer updatePwdById(String pwd, Integer id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pwd", pwd);
		map.put("id", id);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePwdById"), map);
	}
	
	public Integer countUserByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByAccount"),account);
	}

	@Override
	public Company queryByMobile(String mobile) {
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByMobile"),mobile);
	}

	@Override
	public Integer updateCompanyByMyrc(String contact, String mobile,
			Integer sex, String email,Integer id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contact", contact);
		map.put("mobile", mobile);
		map.put("sex",sex);
		map.put("email", email);
		map.put("id", id);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByMyrc"),map);
	}

	@Override
	public Company queryByAccount(String account) {
		return (Company) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByAccount"),account);
	}

}
