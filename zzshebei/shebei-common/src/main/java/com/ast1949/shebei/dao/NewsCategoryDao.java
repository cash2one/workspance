/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 下午03:54:18
 */
package com.ast1949.shebei.dao;

import java.util.List;

import com.ast1949.shebei.domain.NewsCategory;

public interface NewsCategoryDao {

	public List<NewsCategory> queryAllNewsCategory();

	public NewsCategory queryNameAndKeywordsByCode(String category);
}
