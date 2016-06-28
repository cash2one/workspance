package com.ast.ast1949.persist.download.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.download.DownloadInfoDao;

/**
 * author:kongsj date:2013-6-8
 */
@Component("downloadInfoDao")
public class DownloadInfoDaoImpl extends BaseDaoSupport implements
		DownloadInfoDao {

	final static String SQL_FIX = "downloadInfo";

	@Override
	public Integer insert(DownloadInfo downloadInfo) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), downloadInfo);
	}

	@Override
	public DownloadInfo queryById(Integer id) {
		return (DownloadInfo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DownloadInfo> queryList(DownloadInfo downloadInfo,
			PageDto<DownloadInfo> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", downloadInfo);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(DownloadInfo downloadInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", downloadInfo);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public Integer update(DownloadInfo downloadInfo) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), downloadInfo);
	}

	@Override
	public Integer updateDownloadCountByClick(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateDownloadCountByClick"),id);
	}

	@Override
	public Integer updateViewCountByClick(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateViewCountByClick"), id);
	}

	@Override
	public Integer updateToDel(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateToDel"), id);
	}

	@Override
	public Integer countAllFile() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAllFile"));
	}

	@Override
	public DownloadInfo queryByFileUrl(String fileUrl) {
		return (DownloadInfo) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByFileUrl"), fileUrl);
	}
	

}
