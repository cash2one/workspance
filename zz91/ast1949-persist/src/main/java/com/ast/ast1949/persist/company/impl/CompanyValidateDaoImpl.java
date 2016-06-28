/**
 * 
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyValidate;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyValidateDao;

/**
 * @author mays
 *
 */
@Component("companyValidateDao")
public class CompanyValidateDaoImpl extends BaseDaoSupport implements CompanyValidateDao {

	private static String SQL_PREFIX = "companyValidate";
	
	@Override
	public Integer insert(CompanyValidate cv) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), cv);
	}

	@Override
	public CompanyValidate queryOneByKey(String key) {
		
		return (CompanyValidate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOneByKey"), key);
		
	}

	@Override
	public Integer updateActived(Integer id, Integer activedType) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("activedType", activedType);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateActived"), root);
	}

	@Override
	public CompanyValidate queryOneByAccount(String account) {
		return (CompanyValidate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOneByAccount"), account);
	}

	@Override
	public Integer queryActived(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryActived"), account);
	}

	@Override
	public CompanyValidate queryValidateByCompanyId(Integer companyId) {
		return (CompanyValidate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryValidateByCompanyId"), companyId);
	}

	@Override
	public Integer countValidateByCondition(String account, Integer activedType, String gmtCreated) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("account", account);
		map.put("activedType", activedType);
		map.put("gmtCreated", gmtCreated);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countValidateByCondition"), map);
	}

}
