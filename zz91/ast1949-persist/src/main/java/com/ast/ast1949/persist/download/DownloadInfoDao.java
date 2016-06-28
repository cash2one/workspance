package com.ast.ast1949.persist.download;

import java.util.List;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.dto.PageDto;

/**
 * author:kongsj date:2013-6-8
 */
public interface DownloadInfoDao {
	public DownloadInfo queryById(Integer id);

	public Integer insert(DownloadInfo downloadInfo);

	public Integer update(DownloadInfo downloadInfo);

	public List<DownloadInfo> queryList(DownloadInfo downloadInfo,PageDto<DownloadInfo> page);

	public Integer queryListCount(DownloadInfo downloadInfo);

	public Integer updateViewCountByClick(Integer id);

	public Integer updateDownloadCountByClick(Integer id);
	
	public Integer updateToDel(Integer id);
	
	public Integer countAllFile();
	
	public DownloadInfo queryByFileUrl(String fileUrl);
}
