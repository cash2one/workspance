/*
 * 文件名称：NewsService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.news.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.common.VideoDao;
import com.zz91.ep.dao.news.NewsDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.domain.common.Video;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.ep.dto.news.NewsSearchDto;
import com.zz91.ep.service.news.NewsService;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：资讯信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsDao newsDao;
	
	@Resource 
	private PhotoDao photoDao;
	
	@Resource
	private VideoDao videoDao;
	@Override
	public List<News> queryNewsByCategory(String code, Integer size) {
		if (size > 100) {
			size = 100;
		}
		List<News> list = newsDao.queryNewsByCategory(code, size);
		//这里设置htmlPath代替categoryName,用于存放放类别名称。
		for (News news:list) {
			news.setHtmlPath(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, news.getCategoryCode()));
		}
		return list;
	}

	@Override
	public List<CommonDto> queryNewsByRecommend(String code, Integer size) {
		if (size > 100) {
			size = 100;
		}
		List<CommonDto> list = newsDao.queryNewsByRecommend(code, size);
		for (CommonDto dto:list) {
			dto.setName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, dto.getCode()));
		 	 if(dto.getDetails().indexOf("<img")!=-1){
		 		 dto.setOrPhoto("1");
		 	 }
		 	Video video=videoDao.queryByTypeAndId(dto.getId(), "1");
		 	if(video!=null && video.getContent()!=null){
		 		 dto.setVideoUrl(video.getContent());
		 	}

		}
		return list;
	}

	@Override
	public List<News> queryTopNews(Integer size) {
		if (size > 100) {
			size = 100;
		}
		return newsDao.queryTopNews(size);
	}

	@Override
	public PageDto<NewsSearchDto> queryValueByTypeAndKey(String categoryKey, PageDto<NewsSearchDto> page) {
		if(page.getSort()==null){
			page.setSort("gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		List<NewsSearchDto> list = newsDao.queryNewsByCategoryKey(categoryKey, page);
		for (NewsSearchDto dto:list) {
			dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, dto.getCategoryCode()));
		}
		page.setRecords(list);
		page.setTotals(newsDao.queryNewsByCategoryKeyCount(categoryKey));
		return page;
	}

	@Override
	public News queryNewDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.queryNewDetailsById(id);
	}

	@Override
	public Integer updateViewCountById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.updateViewCountById(id);
	}

	@Override
	public News queryPrevNewsById(Integer id, String categoryCode) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.queryPrevNewsById(id, categoryCode);
	}

	@Override
	public News queryNextNewsById(Integer id, String categoryCode) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.queryNextNewsById(id, categoryCode);
	}

	@Override
	public PageDto<NewsSearchDto> pageNewsByCategory(String categoryCode, PageDto<NewsSearchDto> page) {
		if(page.getSort()==null){
			page.setSort("gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		List<NewsSearchDto> list = newsDao.queryNewsByCategoryCode(categoryCode, page);
		for (NewsSearchDto dto:list) {
			dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, dto.getCategoryCode()));
		}
		page.setRecords(list);
		page.setTotals(newsDao.queryNewsByCategoryCodeCount(categoryCode));
		return page;
	}


	@Override
	public List<News> queryNewsByCode(String categoryCode, Integer size) {
		return newsDao.queryNewsByCode(categoryCode, size);
	}

	@Override
	public List<NewsDto> queryNewsAndUrlByCode(String code, Integer size) {
		if (size > 100) {
			size = 100;
		}
		List<NewsDto> dtoList=newsDao.queryNewsAndUrlByCode(code, size);
		//这里设置htmlPath代替categoryName,用于存放放类别名称。
		for (NewsDto newsDto:dtoList) {
			newsDto.getNews().setHtmlPath(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, newsDto.getNews().getCategoryCode()));
			if(newsDto.getNews()!=null){
		        Video video=videoDao.queryByTypeAndId(newsDto.getNews().getId(), "1");
		        if(video!=null){
		        	newsDto.setVideoUrl(video.getContent());
		            newsDto.setPhotoCover(video.getPhotoCover()); 	
		        }
		        if(newsDto.getNews().getDetails()!=null){
			    	if(newsDto.getNews().getDetails().indexOf("<img")!=-1){
			    		//如果文章有图片就赋值给1
			    		 newsDto.setOrPhoto("1");
			    	}
			    }
		    }
		}
		
		return dtoList;
	}

}