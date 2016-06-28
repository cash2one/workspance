/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyAccountDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-28
 */
@Component("companyAccountDao")
public class CompanyAccountDaoImpl extends BaseDaoSupport implements CompanyAccountDao {

	final static String SQL_PREFIX="companyAccount";
	@Override
	public CompanyAccount queryAdminAccountByCompanyId(Integer id) {
		
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAdminAccountByCompanyId"), id);
	}
	@Override
	public CompanyAccount queryAccountByAccount(String account) {
		
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByAccount"), account);
	}
	@Override
	public Integer queryCompanyIdByEmail(String email) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByEmail"), email);
	}
	@Override
	public Integer queryCompanyIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByAccount"), account);
	}
	@Override
	public Integer updatePassword(String account, String password) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("account", account);
		root.put("password", password);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePassword"), root);
	}
	@Override
	public Integer insertAccount(CompanyAccount account) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertAccount"), account);
	}
	@Override
	public Integer updateAccountByUser(CompanyAccount account) {
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAccountByUser"), account);
	}
	@Override
	public Integer countAccountOfMobile(String mobile) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countAccountOfMobile"), mobile);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAccount> queryAccountOfCompany(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAccountOfCompany"), companyId);
	}
	@Override
	public Integer updateAccountByAdmin(CompanyAccount account) {
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAccountByAdmin"), account);
	}
	@Override
	public Integer updateLoginInfo(String account, String ip) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("ip", ip);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateLoginInfo"), root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAccount> queryAccountByAdmin(CompanyAccount account,CompanyAccountSearchDto searchDto,PageDto<CompanyDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("searchDto", searchDto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByAdmin"), root);
	}
	@Override
	public Integer queryAccountByAdminCount(CompanyAccount account,CompanyAccountSearchDto searchDto) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByAdminCount"), root);
	}
	@Override
	public String queryContactByAccount(String senderAccount) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryContactByAccount"), senderAccount);
	}
	@Override
	public Integer queryCompanyIdByMobile(String mobile) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByMobile"), mobile);
	}
	@Override
	public CompanyAccount queryAccountByProductId(Integer productId) {
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByProductId"), productId);
	}
	@Override
	public CompanyAccount queryAccountByCompanyId(Integer id) {
		return (CompanyAccount) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAccountByCompanyId"),id);
	}
	@Override
	public String queryMobileByCompanyId(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMobileByCompanyId"), companyId);
	}
	@Override
	public Integer updateInfoByaccount(CompanyAccount account) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateInfoByaccount"),account);
	}
	@Override
	public Integer countUserByEmail(String email) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUserByEmail"),email);
	}
	@Override
	public Integer queryCompanyIdByNameAndPassw(String username, String password) {
	    Map<String,String> map=new HashMap<String,String>();
	    map.put("username", username);
	    map.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByNameAndPassw"),map);
	}
	@Override
	public String queryCompanyAccountByCompanyId(Integer companyId) {		
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyAccountByCompanyId"), companyId);
	}
	@Override
	public void updateAccountByAccount(CompanyAccount account) {
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAccountByAccount"), account);
	}
}
