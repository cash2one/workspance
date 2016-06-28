package com.zz91.ep.admin.service.news;

import java.util.List;

import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;

public interface NewsService {
 
    public static final int MAX_SIZE = 5;
     
    public List<NewsDto> buildNewsDto(List<News> list);
    
    /**
     * 根据推荐类型取焦点导读（焦点和热点资讯）
     * @param type RECOMMEND_TOP：热点资讯 RECOMMEND_FOCUS：焦点资讯
     * @param max 最大值
     * @return
     */
//    public List<News> queryNewsByRecommend(String type, Integer max);
    /**
     * 查询TOP榜新闻信息(按点击数排序）
     * @param max 最大值，默认为MAX_SIZE
     * @return
     */
//    public List<News> queryTopNews(Integer max);

    /**
     * 根据类别查询资讯信息数(用于分页)
     * @param categoryCode 类别编号
     * @param page
     * @return
     */
//    public PageDto<NewsDto> pageNewsByCategory(String categoryCode, PageDto<NewsDto> page);
    
    /**
     * 根据类别查询推荐资讯信息数(用于分页)
     * @param categoryCode 类别编号
     * @param page
     * @return
     */
//    public PageDto<NewsDto> pageNewsByCategoryKey(String categoryKey, PageDto<NewsDto> page);
    
    /**
     * 根据ID查询资讯详细信息
     * @param id
     * @return
     */
    public News queryNewDetailsById(Integer id);
    /**
     * 查询相关资讯（资讯内容中包含该标签的相关资讯）
     * @param tag 标签内容（为第1个标签）
     * @param max 最大值
     * @return
     */
//    public List<News> queryRelatedNewsByTag(String tag, Integer max);
    /**
     * 根据不同类别查询最新发布资讯信息
     * @param categoryCode 
     * @param max
     * @param week 
     * @return
     */
//    public List<News> queryNewestNewsByCategory(String categoryCode, Integer max);
    /**
     * 上一篇文章
     * @param id
     * @param categoryCode
     * @return
     */
//    public News queryOnNewsById(Integer id,String categoryCode);
    /**
     * 下一篇文章
     * @param id
     * @param categoryCode 类别编号
     * @return
     */
//    public News queryDownNewsById(Integer id,String categoryCode);
    /**
     * 根据标题查询资讯
     */
//    public PageDto<NewsDto> pageNewsBySearchEngine(String title,PageDto<NewsDto> page);
    /**
     * 添加一条新的资讯记录(后台)
     * @param news
     * @param admin 发布人
     * @return
     */
    public Integer createNewsByAdmin(News news,String admin);
    /**
     * 更新资讯信息(后台)
     * @param news
     * @return
     */
    public Integer updateNews(News news);
    /**
     * 删除资讯信息(后台)
     * @param id
     * @return
     */
    public Integer deleteNewsById(Integer id);
    /**
     * 更新资讯状态(后台)
     * @param id
     * @param status 0.(默认值)暂不发布 1.已发布
     * @return
     */
    public Integer updateStatusOfNews(Integer id,Integer status);
    /**
     * 查询资讯列表(后台)
     * @param news
     * @param type 推荐类型
     * @return
     */
    public PageDto<NewsDto> pageNewsByAdmin(String code,String title,Integer status,String type,PageDto<NewsDto> page);

    /**
     * 推荐新闻
     * @param id
     * @param type
     * @return
     */
	public Integer updateNewsRecommend(Integer id, String type);

	/**
	 * 取消推荐
	 * @param id
	 * @return
	 */
	public Integer cancelRecommendNews(Integer id,Integer rid);
	
//	public Integer updateViewCountById(Integer id);
	
	/**
	 * 根据关键字查询资讯内容(内容表题包含关键字)
	 * @param keywords
	 * @param size
	 * @return
	 */
//	public List<News> queryNewByKeywords(String keywords, Integer size);

	/**
	 * 查询资讯/焦点(周报)
	 * @param type
	 * @param size
	 * @param recommend
	 * @param category
	 * @return
	 */
	public List<News> queryRecommendNewByWeekly(String type, Integer size, Integer recommend,
			String category);
	
	
	/**
	 * 判断新闻标题是否重复
	 * @param type
	 * @param size
	 * @param recommend
	 * @param category
	 * @return
	 */
	public Integer countByTitle(String title);
	
	/**
	 * 检索资讯 在指定时间范围
	 * @param from
	 * @param to
	 * @return
	 */
	public List<News> queryListByFromTo(String from, String to);
	

}