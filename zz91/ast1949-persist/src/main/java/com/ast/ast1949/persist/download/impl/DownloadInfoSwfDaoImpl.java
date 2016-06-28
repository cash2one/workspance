package com.ast.ast1949.persist.download.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.download.DownloadInfoSwf;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.download.DownloadInfoSwfDao;

/**
 *	author:kongsj
 *	date:2013-6-8
 */
@Component("downloadInfoSwf")
public class DownloadInfoSwfDaoImpl extends BaseDaoSupport implements DownloadInfoSwfDao{
	
	final static String SQL_FIX = "downloadInfoSwf";

	@Override
	public Integer insert(DownloadInfoSwf downloadInfoSwf) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), downloadInfoSwf);
	}

	@Override
	public DownloadInfoSwf queryById(Integer id) {
		return (DownloadInfoSwf) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DownloadInfoSwf> queryListByDownloadId(Integer downloadId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryListByDownloadId"), downloadId);
	}

}
