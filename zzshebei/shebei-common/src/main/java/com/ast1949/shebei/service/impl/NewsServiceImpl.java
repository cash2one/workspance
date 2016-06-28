package com.ast1949.shebei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.NewsDao;
import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.NewsService;

@Component("newsService")
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsDao newsDao;
	
	@Override
	public List<News> queryNewsByCategoryAndType(String category, Short type,
			Integer size,Short flag) {
		return newsDao.queryNewsByCategoryAndType(category, type, size,flag);
	}

	@Override
	public PageDto<News> pageNews(String category, Short type,
			PageDto<News> page) {
		if(page.getSort()==null){
			page.setSort("n.gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(newsDao.queryNews(category,type,page));
		page.setTotals(newsDao.queryNewsCount(category,type));
		return page;
	}

	@Override
	public News queryNewsById(Integer id,Short type) {
		return newsDao.queryNewsById(id,type);
	}

	@Override
	public News queryOnNewsById(Integer id, String categoryCode,Short type) {
		return newsDao.queryOnNewsById(id, categoryCode,type);
	}

	@Override
	public News queryDownNewsById(Integer id, String categoryCode,Short type) {
		return newsDao.queryDownNewsById(id, categoryCode,type);
	}
	
}
