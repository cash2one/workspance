/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 8, 2010 by Rolyer.
 */
package com.ast.ast1949.persist.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsDO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsDAO {
	/**
	 * 添加新闻
	 * @param news
	 * @return
	 */
	public Integer insertNews(NewsDO news);
	/**
	 * 删除新闻
	 * @param id
	 * @return
	 */
//	public Integer deleteNewsById(Integer id);
	/**
	 * 更新新闻
	 * @param news
	 * @return
	 */
	public Integer updateNewsById(NewsDO news);
	/**
	 * 根据编号查询新闻
	 * @param id
	 * @return
	 */
	public NewsDO queryNewsById(Integer id);
	/**
	 * 获取新闻信息列表
	 * @param newsForFrontDTO
	 * @return
	 */
//	public List<NewsForFrontDTO> queryNewsListForFront(NewsForFrontDTO newsForFrontDTO);
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
