/**
 * @author kongsj
 * @date 2015年5月11日
 * 
 */
package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustDealerDao;

@Component("trustDealerDao")
public class TrustDealerDaoImpl extends BaseDaoSupport implements TrustDealerDao {

	final static String SQL_FIX = "trustDealer";

	@Override
	public Integer insert(TrustDealer trustDealer) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), trustDealer);
	}

	@Override
	public Integer update(TrustDealer trustDealer) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), trustDealer);
	}

	@Override
	public TrustDealer queryById(Integer id) {
		return (TrustDealer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustDealer> queryByCondition(TrustDealer trustDealer,PageDto<TrustDealer> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustDealer", trustDealer);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(TrustDealer trustDealer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustDealer", trustDealer);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustDealer> queryAllDealer() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllDealer"));
	}

	@Override
	public Integer deleteDealer(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteDealer"), id);
	}
	

}
