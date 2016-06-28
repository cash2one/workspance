package com.ast.ast1949.persist.bbs.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostTypeDao;

@Component("bbsPostTypeDao")
public class BbsPostTypeDaoImpl extends BaseDaoSupport implements
		BbsPostTypeDao {

	final static String SQL_FIX = "bbsPostType";

	public BbsPostType queryPostTypeById(Integer id) {
		return (BbsPostType) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPostTypeById"), id);
	}
}
