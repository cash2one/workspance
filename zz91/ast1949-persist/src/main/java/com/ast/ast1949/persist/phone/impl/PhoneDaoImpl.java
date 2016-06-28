package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneDao;

/**
 * author:kongsj date:2013-7-3
 */
@Component("phoneDao")
public class PhoneDaoImpl extends BaseDaoSupport implements PhoneDao {

	final static String SQL_FIX = "phone";

	@Override
	public Integer insert(Phone phone) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phone);
	}

	@Override
	public Phone queryById(Integer id) {
		return (Phone) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer update(Phone phone) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), phone);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Phone> queryList(Phone phone, PageDto<Phone> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(Phone phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public Integer updateAmountAndBalance(Integer id, String amount,
			String balance) {
		Map<String , Object>map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("amount", amount);
		map.put("balance", balance);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateAmountAndBalance"), map);
	}

	@Override
	public Integer countByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByAccount"), account);
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), companyId);
	}

	@Override
	public Integer deleteById(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteById"),id);
	}

	@Override
	public Phone queryByCompanyId(Integer companyId) {
		return (Phone) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

	@Override
	public Phone queryByTel(String tel) {
		return (Phone) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByTel"), tel);
	}

}
