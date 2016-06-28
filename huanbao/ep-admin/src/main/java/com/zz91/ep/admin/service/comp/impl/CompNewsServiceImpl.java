/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.comp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.comp.CompNewsDao;
import com.zz91.ep.admin.service.comp.CompNewsService;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.util.Assert;

/**
 * @author totly
 *
 * created on 2011-9-16
 */
@Transactional
@Service("compNewsService")
public class CompNewsServiceImpl implements CompNewsService{

    @Resource
    private CompNewsDao compNewsDao;

//	@Override
//	public List<CompNews> queryCompNewsByCid(Integer cid, String type,
//			Short pause, Short check, Integer size) {
////		Assert.notNull(cid, "the cid can not be null");
//		return compNewsDao.queryCompNewsByCid(cid, type, pause, check, size);
//	}

//	@Override
//	public PageDto<CompNews> pageCompNewsByCid(Integer cid, String type, String keywords,
//			Short pause, Short check,Short delete, PageDto<CompNews> page) {
//		page.setRecords(compNewsDao.queryCompNewsByCid(cid, type, keywords, pause, check, delete, page));
//		page.setTotals(compNewsDao.queryCompNewsByCidCount(cid, type, keywords, pause, delete, check));
//		return page;
//	}

//	@Override
//	public CompNews queryCompNewsById(Integer id) {
//		Assert.notNull(id, "the id can not be null");
//		return compNewsDao.queryCompNewsById(id);
//	}

//	@Override
//	public Integer createArticle(CompNews compNews) {
//		Assert.notNull(compNews, "the compNews can not be null");
//		Assert.notNull(compNews.getCategoryCode(), "the compNews.getCategoryCode() can not be null");
//		Assert.notNull(compNews.getCid(), "the compNews.getCid() can not be null");
//		return compNewsDao.insertArticle(compNews);
//	}

	@Override
	public Integer deleteArticle(Integer id, Integer cid) {
		return compNewsDao.updateDeleteStatusByCid(id,cid,(short)0);
	}

//	@Override
//	public Integer publishArticle(Integer id, Integer cid, Short status) {
//		return compNewsDao.updatePauseStatusByCid(id, cid, status);
//	}

//	@Override
//	public Integer updateArticle(CompNews compNews) {
//		Assert.notNull(compNews, "the compNews can not be null");
//		Assert.notNull(compNews.getId(), "the compNews.getId() can not be null");
//		Assert.notNull(compNews.getCid(), "the compNews.getCid() can not be null");
//		return compNewsDao.updateArticle(compNews);
//	}

	@Override
	public Integer updateCheckStatus(Integer id, Short status,String account) {
		return compNewsDao.updateCheckStatus(id,status,account);
	}

	@Override
	public CompNews queryDetailsById(Integer id) {
		return compNewsDao.queryDetailsById(id);
	}

	@Override
	public Integer updateContent(Integer id, String content,String title,String categoryCode,String tags) {
		return compNewsDao.updateContent(id,content,title,categoryCode,tags);
	}

	@Override
	public PageDto<CompNewsDto> pageCompNewsByAdmin(Integer cid,String type,
			String title,Short pause, Short check, Short delete,
			PageDto<CompNewsDto> page) {
		if (page.getSort()==null){
			page.setSort("cn.gmt_publish");
		}
		if (page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(compNewsDao.queryCompNewsByAdmin(cid,type,title,pause,check,delete,page));
		page.setTotals(compNewsDao.queryCompNewsCountByAdmin(cid,type,title,pause,check,delete));
		return page;
	}

	@Override
	public Integer queryNewCount(Integer cid, Short pause, Short check,
			Short delete) {
		return compNewsDao.queryCompNewsCountByAdmin(cid, null, null, pause, check, delete);
	}
}