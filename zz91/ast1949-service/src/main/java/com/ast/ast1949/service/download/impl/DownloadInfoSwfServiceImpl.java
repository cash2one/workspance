package com.ast.ast1949.service.download.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ast.ast1949.domain.download.DownloadInfoSwf;
import com.ast.ast1949.persist.download.DownloadInfoSwfDao;
import com.ast.ast1949.service.download.DownloadInfoSwfService;

/**
 * author:kongsj
 * date:2013-6-8
 */
public class DownloadInfoSwfServiceImpl implements DownloadInfoSwfService {

	@Resource
	private DownloadInfoSwfDao downloadInfoSwfDao;

	@Override
	public DownloadInfoSwf queryById(Integer id) {
		if (id == null) {
			return null;
		}
		return downloadInfoSwfDao.queryById(id);
	}

	@Override
	public List<DownloadInfoSwf> queryListByDownloadId(Integer downloadId) {
		if (downloadId == null) {
			return null;
		}
		return downloadInfoSwfDao.queryListByDownloadId(downloadId);
	}

	@Override
	public Integer insert(DownloadInfoSwf downloadInfoSwf) {
		return downloadInfoSwfDao.insert(downloadInfoSwf);
	}

}
