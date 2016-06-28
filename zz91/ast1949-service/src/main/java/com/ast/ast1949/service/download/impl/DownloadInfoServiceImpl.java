package com.ast.ast1949.service.download.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.download.DownloadInfoDao;
import com.ast.ast1949.service.download.DownloadInfoService;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.SwfUtils;
import com.zz91.util.lang.StringUtils;

/**
 *	author:kongsj
 *	date:2013-6-8
 */
@Component("downloadInfoService")
public class DownloadInfoServiceImpl implements DownloadInfoService{

	private final static String RESOURCE_URL = "http://img1.zz91.com";
	
	@Resource
	private DownloadInfoDao downloadInfoDao;
	
	@Override
	public Integer insert(DownloadInfo downloadInfo) {
//		downloadInfo.setLanguage("简体中文");
//		downloadInfo.setType("PDF");
		downloadInfo.setIsDeleted(IS_NOT_DELETED);
//		Long l = FileUtils.getFileSize(DATA_URL+downloadInfo.getFileUrl());
//		downloadInfo.setSize(String.valueOf(l/1000));
		return downloadInfoDao.insert(downloadInfo);
	}

	@Override
	public DownloadInfo queryById(Integer id) {
		if(id==null){
			return null;
		}
		return downloadInfoDao.queryById(id);
	}

	@Override
	public PageDto<DownloadInfo> pageList(DownloadInfo downloadInfo,
			PageDto<DownloadInfo> page) {
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("id");
		}
		page.setTotalRecords(downloadInfoDao.queryListCount(downloadInfo));
		page.setRecords(downloadInfoDao.queryList(downloadInfo, page));
		return page;
	}
	
	@Override
	public List<DownloadInfo> queryList(DownloadInfo downloadInfo,PageDto<DownloadInfo> page){
		return downloadInfoDao.queryList(downloadInfo, page);
	}

	@Override
	public Integer update(DownloadInfo downloadInfo) {
		if(downloadInfo.getId()==null){
			return 0;
		}
		return downloadInfoDao.update(downloadInfo);
	}

	@Override
	public Integer updateDownloadCountByClick(Integer id) {
		if(id==null){
			return 0;
		}
		return downloadInfoDao.updateDownloadCountByClick(id);
	}

	@Override
	public Integer updateViewCountByClick(Integer id) {
		if(id==null){
			return 0;
		}
		return downloadInfoDao.updateViewCountByClick(id);
	}
	
	@Override
	public Integer deleteById(Integer id){
		if(id==null){
			return 0;
		}
		return downloadInfoDao.updateToDel(id);
	}

	@Override
	public Boolean pdfToSwf(Integer id) {
		if(id == null){
			return false;
		}
		DownloadInfo downloadInfo = queryById(id);
		if(downloadInfo==null||StringUtils.isEmpty(downloadInfo.getFileUrl())){
			return false;
		}
		String sourceFilePath = MvcUpload.getDestRoot() + downloadInfo.getFileUrl();
//		String sourceFilePath = "http://img1.zz91.com/xiazai/2013/6/21/c1743299-5566-425b-90e2-4affdfac8cd2.pdf";
		String swfFilePath =MvcUpload.getDestRoot() + downloadInfo.getFileUrl().replaceAll(".pdf", "")+"/%.swf";
		try {
			SwfUtils.convertFileToSwf(sourceFilePath, swfFilePath);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public Boolean pdfToSwf(String fileUrl) {
		if(StringUtils.isEmpty(fileUrl)){
			return false;
		}
		String sourceFilePath = MvcUpload.getDestRoot() + fileUrl;
		String swfFilePath =sourceFilePath.replaceAll(".pdf", "")+"/%.swf";
		try {
			SwfUtils.convertFileToSwf(sourceFilePath, swfFilePath);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Integer countAllFile() {
		Integer i = downloadInfoDao.countAllFile();
		if(i<200){
			return i+200;
		}else{
			return i;
		}
	}

	@Override
	public DownloadInfo queryByFileUrl(String fileUrl) {
		return downloadInfoDao.queryByFileUrl(fileUrl);
	}

}
