/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-8-16
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsViewLogDao;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-8-16
 */
@Component("bbsViewLogDao")
public class BbsViewLogDaoImpl extends BaseDaoSupport implements BbsViewLogDao {

	final static String SQL_PREFIX="bbsViewLog";
	
	@Override
	public Integer countViewLog(Integer postId, long targetDate) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("gmtTarget", targetDate);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countViewLog"), root);
	}

	@Override
	public Integer insertViewNum(Integer postId, long targetDate) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("gmtTarget", targetDate);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertViewNum"), root);
	}

	@Override
	public Integer updateViewLogNum(Integer postId, long targetDate) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("postId", postId);
		root.put("gmtTarget", targetDate);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateViewLogNum"), root);
	}

}
