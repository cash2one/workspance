/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 下午03:56:04
 */
package com.ast1949.shebei.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.BaseDao;
import com.ast1949.shebei.dao.NewsCategoryDao;
import com.ast1949.shebei.domain.NewsCategory;

@Component("newsCategoryDao")
public class NewsCategoryDaoImpl extends BaseDao implements NewsCategoryDao{
	
	final static String SQL_PREFIX="newsCategory";

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsCategory> queryAllNewsCategory() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllNewsCategory"));
	}

	@Override
	public NewsCategory queryNameAndKeywordsByCode(String category) {
		return (NewsCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameAndKeywordsByCode"), category);
	}

}
