/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 下午03:44:28
 */
package com.ast1949.shebei.service;

import java.util.List;

import com.ast1949.shebei.domain.NewsCategory;

public interface NewsCategoryService {

	/**
	 * 查询资讯报价类别
	 * @return
	 */
	public List<NewsCategory> queryAllNewsCategory();

	/**
	 * 查询类别名称
	 * @param category
	 * @return
	 */
	public NewsCategory queryNameAndKeywordsByCode(String category);
}
