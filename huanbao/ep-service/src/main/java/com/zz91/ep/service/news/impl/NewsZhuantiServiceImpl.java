package com.zz91.ep.service.news.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.dao.news.NewsZhuantiDao;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.service.news.NewsZhuantiService;
/** 
 * 专题service
 * @author 黄怀清 
 * @version 创建时间：2012-9-18
 */
@Transactional
@Service("zhuantiServiceImpl")
public class NewsZhuantiServiceImpl implements NewsZhuantiService{
	@Resource
	private NewsZhuantiDao zhuantiDao;
	
	@Override
	public PageDto<Zhuanti> pageByCategory(String category,
			PageDto<Zhuanti> page) {
		
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		if(page.getSort()==null){
			page.setSort("nz.gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(zhuantiDao.queryByCategory(category, page));
		page.setTotals(zhuantiDao.queryByCategoryCount(category));
		return page;
	}
	@Override
	public List<Zhuanti> queryByCategory(String category,
			Integer size){
		PageDto<Zhuanti> page =new PageDto<Zhuanti>();
		if(page.getLimit()==null){
			page.setLimit(20);
		}else{
			page.setLimit(size);
		}
		if(page.getSort()==null){
			page.setSort("nz.gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		
		return zhuantiDao.queryByCategory(category, page);
	}
	@Override
	public List<Zhuanti> queryAttention(Integer size){
		if(size==null){
			size=20;
		}
		return zhuantiDao.queryAttention(size);
	}
	@Override
	public List<Zhuanti> queryRecommend(Integer size){
		if(size==null){
			size=20;
		}
		return zhuantiDao.queryRecommend(size);
	}
	
}
