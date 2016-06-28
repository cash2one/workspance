/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-28
 */
package com.zz91.ep.admin.dao.news;

import java.util.List;

import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;

/**
 * @author totly
 *
 * created on 2011-9-28
 */
public interface NewsDao {

    /**
     * 根据推荐类型取焦点导读（焦点和热点资讯）
     * type：RECOMMEND_TOP：热点资讯
     * RECOMMEND_FOCUS：焦点资讯
     * max：最大值，默认MAX_SIZE
     */
//    public List<News> queryNewsByRecommend(String type, Integer max);
    
    /**
     * 查询TOP榜新闻信息(按点击数排序）
     * max：最大值，默认为MAX_SIZE
     */
//	public List<News> queryTopNews(Integer max);
    
    /**
     * 根据类别查询资讯列表
     * categoryCode：类别编号
     * max：最大值，默认为MAX_SIZE 
     */
//	public List<News> queryNewsByCategory(String categoryCode, PageDto<NewsDto> page);
	
    /**
     * 根据类别查询推荐资讯列表
     * categoryCode：类别编号
     * max：最大值，默认为MAX_SIZE 
     */
//	public List<News> queryNewsByCategoryKey(String categoryKey, PageDto<NewsDto> page);
	
    /**
     * 根据类别查询资讯列表(分页)
     * categoryCode：类别编号
     * page：分页相关参数
     * 
     */
//	public Integer queryNewsByCategoryCount(String categoryCode);
	
    /**
     * 根据类别查询推荐资讯列表(分页)
     * categoryCode：类别编号
     * page：分页相关参数
     * 
     */
//	public Integer queryNewsByCategoryKeyCount(String categoryKey);
	
    /**
     * 根据ID查询资讯详细信息
     * @param id
     * @return
     */
	public News queryNewDetailsById(Integer id);
    /**
     * 查询相关资讯（资讯内容中包含该标签的相关资讯）
     * @param tag 标签内容（为第1个标签）
     * @param max 最大值，默认为MAX_SIZE
     * @return
     */
//	public List<News> queryRelatedNewsByTag(String tag, Integer max);
    /**
     * 根据不同类别查询最新发布资讯信息
     * @param categoryCode 类别编号（null时为查询所有资讯信息）
     * @param max 最大值，默认为MAX_SIZE
     * @return
     */
//	public List<News> queryNewestNewsByCategory(String categoryCode, Integer max);
	
	/**
	 * 查询上一篇文章
	 * 
	 */
//	public News queryOnNewsById(Integer id,String categoryCode);
	/**
	 * 查询下篇文章
	 * 
	 */
//	public News queryDownNewsById(Integer id,String categoryCode);
	/**
	 * 根据标题搜索资讯
	 * @param title 文章标题
	 */
//	public List<News> queryNewsByTitle(String title,PageDto<NewsDto> page);
	/**
	 * 根据标题搜索资讯数量
	 * @param title 文章标题
	 */
//	public Integer queryNewsByTitleCount(String title);
	/**
	 * 添加一条新的资讯(后台)
	 * @param news
	 * @param admin
	 * @return
	 */
	public Integer insertNewsByAdmin(News news);
	/**
	 * 更新资讯(后台)
	 * @param news
	 * @return
	 */
	public Integer updateNews(News news);
	/**
	 * 删除推荐信息(后台)
	 * @param id 资讯编号
	 * @return
	 */
	public Integer deleteNewsById(Integer id);
	/**
	 * 更新资讯的发布状态(后台)
	 * @param id 资讯编号
	 * @param status 发布状态
	 * @return
	 */
	public Integer updateStatusOfNews(Integer id,Integer status);
	/**
	 * 查询资讯列表(用于后台)
	 * @param news
	 * @param type 推荐类型
	 * @return
	 */
	public List<NewsDto> queryNewsByAdmin(NewsDto news,PageDto<NewsDto> page);
	/**
	 * 查询资讯列表数量(用于后台)
	 * @param news
	 * @param type 推荐类型
	 * @return
	 */
	public Integer queryNewsByAdminCount(NewsDto news);
	//更新浏览数
//	public Integer updateViewCountById(Integer id);
	
	/**
	 * 根据关键字查询资讯(标题包含关键字)
	 * @param keywords
	 * @param size
	 * @return
	 */
//	public List<News> queryNewByKeywords(String keywords, Integer size);

	public List<News> queryRecommendNewByWeekly(String type, Integer size,
			Integer recommend, String category);
	
	public Integer countByTitle(String title);

	/**
	 * 检索资讯 在指定时间范围
	 * @param from
	 * @param to
	 * @return
	 */
	public List<News> queryListByFromTo(String from, String to);
	
}