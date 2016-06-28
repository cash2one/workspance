package com.ast.ast1949.service.download;

import java.util.List;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.dto.PageDto;

/**
 *	author:kongsj
 *	date:2013-6-8
 */
public interface DownloadInfoService {
	
	final static String IS_DELETED = "1";
	final static String IS_NOT_DELETED = "0";
	
	public DownloadInfo queryById(Integer id);

	public Integer insert(DownloadInfo downloadInfo);

	public Integer update(DownloadInfo downloadInfo);

	public PageDto<DownloadInfo> pageList(DownloadInfo downloadInfo,PageDto<DownloadInfo> page);

	public Integer updateViewCountByClick(Integer id);

	public Integer updateDownloadCountByClick(Integer id);

	public Integer deleteById(Integer id);
	
	/**
	 * PDF文件 转化为 SWF文件
	 * @param downloadInfo
	 * @return
	 */
	public Boolean pdfToSwf(Integer id);

	public Boolean pdfToSwf(String fileUrl);
	
	public List<DownloadInfo> queryList(DownloadInfo downloadInfo,PageDto<DownloadInfo> page);
	
	public Integer countAllFile();
	
	public DownloadInfo queryByFileUrl(String fileUrl);

}
