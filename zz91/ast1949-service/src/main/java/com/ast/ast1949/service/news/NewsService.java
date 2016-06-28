/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsDO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsService {
	/**
	 * 添加新闻
	 * 
	 * @param news
	 * @return
	 */
	public Integer insertNews(NewsDO news);

	/**
	 * 删除新闻
	 * 
	 * @param id
	 * @return
	 */
//	public Integer deleteNewsById(Integer id);

	/**
	 * 更新新闻
	 * 
	 * @param news
	 * @return
	 */
//	public Integer updateNewsById(NewsDO news);

	/**
	 * 根据编号查询新闻
	 * 
	 * @param id
	 * @return
	 */
//	public NewsDO queryNewsById(Integer id);

	/**
	 * 更新新闻,先判断再更新
	 * 
	 * @param id
	 * @return
	 */
	public Integer update(NewsDO news);
	
	/**
	 * 
	 * 获取新闻信息列表
	 * 
	 * @param moduleId
	 *            新闻模块,该值为空时不限模块；
	 * @param tradeCode
	 *            所属行业,该值为空时不限行业；
	 * @param gmtPublished
	 *			  发布时间 
	 * @param startIndex
	 *            当startIndex为null时值:0；
	 * @param num
	 *            查询记录,默认值:20；
	 * @param sort
	 *            排序字段,默认增值:n.gmt_order；
	 * @param dir
	 *            排序方式,默认值:asc；
	 * @param showStyle
	 *            显示样式,不需要显示时，请设置该值为false。
	 * @return
	 */
//	public List<NewsForFrontDTO> queryNewsListForFront(Integer moduleId,
//			String tradeCode,Date gmtPublished, Integer startIndex, Integer num, String sort,
//			String dir, boolean showStyle);
	/**
	 * 根据标题查询新闻
	 * @param title 标题
	 * @param number 查询记录总数，默认为10
	 * @return 返回结果集
	 */
	public List<NewsDO> queryNewsByTitle(String title, Integer number);
	
	/**
	 * 根据moduleId查询新闻
	 * @param moduleId 模块Id
	 * @param size 查询记录总数
	 * @return List<NewsDO>
	 */
	public List<NewsDO> queryNewsByModuleId(Integer moduleId,Integer size);
}
