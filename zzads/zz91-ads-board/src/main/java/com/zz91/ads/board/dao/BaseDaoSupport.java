package com.zz91.ads.board.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 
 * @author liuwb
 * 
 */
public class BaseDaoSupport extends SqlMapClientDaoSupport {

	/**
	 * 获取SQL语句名称
	 * 
	 * @param sqlPreFix
	 *            对应的Ibaits命名空间
	 * @param sqlKey
	 *            对应的SQL语句KEY
	 * @return
	 */
	protected String addSqlKeyPreFix(String sqlPreFix, String sqlKey) {
		StringBuilder buf = new StringBuilder();
		if (sqlPreFix != null && sqlPreFix.trim().length() > 0)
			buf.append(sqlPreFix).append(".").append(sqlKey);
		else
			return sqlKey;
		return buf.toString();
	}

	
}
