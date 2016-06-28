package com.ast1949.shebei.service;

import java.util.List;

import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.dto.PageDto;

public interface NewsService {
	
	final static Short ZIXUN_TYPE=0;
	final static Short BAOJIA_TYPE=1;
	
	public List<News> queryNewsByCategoryAndType(String category,Short type,Integer size, Short flag);

	/**
	 * 类别分页
	 * @param category
	 * @param type
	 * @param page
	 * @return
	 */
	public PageDto<News> pageNews(String category, Short type,
			PageDto<News> page);

	/**
	 * @param id
	 * @param type 
	 * @return
	 */
	public News queryNewsById(Integer id, Short type);
	 /**
     * 上一篇文章
     * @param id
     * @param categoryCode
	 * @param type 
     * @return
     */
    public News queryOnNewsById(Integer id,String categoryCode, Short type);
    /**
     * 下一篇文章
     * @param id
     * @param categoryCode 类别编号
     * @param zixunType 
     * @return
     */
    public News queryDownNewsById(Integer id,String categoryCode, Short type);
}
