package com.zz91.ep.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.news.NewsCategoryDao;
import com.zz91.ep.domain.news.NewsCategory;
/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：资讯信息类别实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-09-03　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("newsCategoryDaoImpl")
public class NewsCategoryDaoImpl extends BaseDao implements NewsCategoryDao {
	
	final static String SQL_PREFIX="newsCategory";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsCategory> querySubNewsCategoryByCode(String code,Integer size) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySubNewsCategoryByCode"), map);
	}

}
