/**
 * @author kongsj
 * @date 2014年9月3日
 * 
 */
package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneLibraryDao;

@Component("phoneLibraryDao")
public class PhoneLibraryDaoImpl extends BaseDaoSupport implements
		PhoneLibraryDao {

	final static String SQL_FIX = "phoneLibrary";

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLibrary> queryList(PhoneLibrary phoneLibrary,
			PageDto<PhoneLibrary> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLibrary", phoneLibrary);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(PhoneLibrary phoneLibrary) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLibrary", phoneLibrary);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public Integer update(PhoneLibrary phoneLibrary) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), phoneLibrary);
	}

	@Override
	public PhoneLibrary queryById(Integer id) {
		return (PhoneLibrary) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@Override
	public PhoneLibrary queryByTel(String tel) {
		return (PhoneLibrary) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryByTel"), tel);
	}

	@Override
	public Integer insert(PhoneLibrary phoneLibrary) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phoneLibrary);
	}

	@Override
	public Integer delNumber(Integer id) {
		
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteNumber"), id);
	}

}
