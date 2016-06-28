package com.ast1949.shebei.dao;

import java.util.Date;
import java.util.List;

import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.dto.PageDto;

public interface NewsDao {

	public Integer insertNews(News news);

	public List<News> queryNewsByCategoryAndType(String category,Short type,Integer size, Short flag);
	/**
	 * 查询(资讯/报价)
	 * @param category
	 * @param type
	 * @param page
	 * @return
	 */
	public List<News> queryNews(String category, Short type, PageDto<News> page);
	/**
	 * 查询(资讯/报价)数量
	 * @param category
	 * @param type
	 * @return
	 */
	public Integer queryNewsCount(String category, Short type);
	/**
	 * @param id
	 * @param type 
	 * @return
	 */
	public News queryNewsById(Integer id, Short type);
	/**
	 * 查询上一篇文章
	 * 
	 */
	public News queryOnNewsById(Integer id,String categoryCode,Short type);
	/**
	 * 查询下篇文章
	 * @param type 
	 * 
	 */
	public News queryDownNewsById(Integer id,String categoryCode, Short type);

	/**
	 * @return
	 */
	public Date queryMaxGmtShow();
	
}
