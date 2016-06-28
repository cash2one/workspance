/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyAccountContactDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-31
 */
@Component("companyAccountContactDao")
public class CompanyAccountContactDaoImpl extends BaseDaoSupport implements CompanyAccountContactDao {

	final static String SQL_PREFIX="companyAccountContact";
	
	@Override
	public List<CompanyAccountContact> queryContactOfAccount(String account) {
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryContactOfAccount"), account);
	}

	@Override
	public Integer deleteContactByAccount(Integer id, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("account", account);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteContactByAccount"), root);
	}

	@Override
	public Integer insertContact(CompanyAccountContact contact) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertContact"), contact);
	}

	@Override
	public List<CompanyAccountContact> queryContactByAccount(String account,
			PageDto<CompanyAccountContact> page,String isHadden) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("page", page);
		root.put("isHadden", isHadden);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryContactByAccount"), root);
	}

	@Override
	public Integer queryContactByAccountCount(String account,String isHadden) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("isHadden", isHadden);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryContactByAccountCount"), root);
	}

	@Override
	public CompanyAccountContact queryOneContactById(Integer id) {
		return (CompanyAccountContact) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOneContactById"), id);
	}

	@Override
	public Integer updateContactById(CompanyAccountContact contact) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateContactById"), contact);
	}
}
