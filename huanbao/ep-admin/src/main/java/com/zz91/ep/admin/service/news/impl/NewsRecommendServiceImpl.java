package com.zz91.ep.admin.service.news.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.news.NewsRecommendDao;
import com.zz91.ep.admin.service.news.NewsRecommendService;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-10-13 
 */
@Component("newsRecommendService")
public class NewsRecommendServiceImpl implements NewsRecommendService {
	
	@Resource
	private NewsRecommendDao newsRecommendDao;
	
//	@Override
//	public Integer recommend(Integer newsId, String type) {
//		Assert.notNull(newsId, "the newsId can not be null");
//		Assert.notNull(newsId, "the newsId can not be null");
//		NewsRecommend recommend =new NewsRecommend();
//		recommend.setNewsId(newsId);
//		recommend.setType(type);
//		return newsRecommendDao.insertRecommend(recommend);
//	}

//	@Override
//	public Integer unRecommend(Integer newsId, String type) {
//		Assert.notNull(newsId, "the newsId can not be null");
//		Assert.notNull(type, "the type can not be null");
//		return newsRecommendDao.deleteRecommendById(newsId, type);
//	}

}
