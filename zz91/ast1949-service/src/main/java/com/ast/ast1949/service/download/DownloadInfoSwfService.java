package com.ast.ast1949.service.download;

import java.util.List;

import com.ast.ast1949.domain.download.DownloadInfoSwf;

/**
 *	author:kongsj
 *	date:2013-6-8
 */
public interface DownloadInfoSwfService{
	public DownloadInfoSwf queryById(Integer id);

	public List<DownloadInfoSwf> queryListByDownloadId(Integer downloadId);

	public Integer insert(DownloadInfoSwf downloadInfoSwf);
}
