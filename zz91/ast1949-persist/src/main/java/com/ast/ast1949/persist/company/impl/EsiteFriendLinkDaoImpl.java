/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-15
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteFriendLinkDo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.EsiteFriendLinkDao;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-15
 */
@Component("esiteFriendLinkDao")
public class EsiteFriendLinkDaoImpl extends BaseDaoSupport implements
		EsiteFriendLinkDao {

	final static String SQL_PREFIX = "esiteFriendLink";

	@Override
	public Integer deleteFriendLinkByCompany(Integer id, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("id", id);
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteFriendLinkByCompany"), root);
	}

	@Override
	public Integer insertFriendLink(EsiteFriendLinkDo friendlink) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertFriendLink"), friendlink);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EsiteFriendLinkDo> queryFriendLinkByCompany(Integer companyId,
			Integer limit) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryFriendLinkByCompany"), root);
	}

	@Override
	public Integer updateFriendLinkByCompany(EsiteFriendLinkDo friendlink) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateFriendLinkByCompany"),
				friendlink);
	}

	@Override
	public Integer countFriendLinkNumByCompany(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countFriendLinkNumByCompany"),
				companyId);
	}

	@Override
	public EsiteFriendLinkDo queryOneFriendLinkById(Integer id) {
		return (EsiteFriendLinkDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryOneFriendLinkById"), id);
	}

}
