/*
 * 文件名称：TradeCategoryDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.TradeCategoryDao;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeProperty;


/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：供求类别信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("tradeCategoryDao")
public class TradeCategoryDaoImpl extends BaseDao implements TradeCategoryDao {

	final static String SQL_PREFIX="tradecategory";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> queryCategoryByParent(String parentCode, Integer deep, Integer max) {
		Map<String, Object> root = new HashMap<String, Object>();
        root.put("parentCode", parentCode);
        root.put("deep", deep);
        root.put("count", max);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParent"), root);
	}

	@Override
	public String queryTagsByCode(String categoryCode) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTagsByCode"), categoryCode);
	}
	
	@Override
	public String queryCodeById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCodeById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeProperty> querySearchPropertyByCategory(String categoryCode) {
		 return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySearchPropertyByCategory"), categoryCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> queryCategoryByTags(String parentCode,
			String tags, Integer deep, Integer max) {
		Map<String, Object> root = new HashMap<String, Object>();
        root.put("parentCode", parentCode);
        root.put("tags", tags);
        root.put("deep", deep);
        root.put("count", max);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByTags"), root);
	}

	@Override
	public Integer queryIsLeafByCode(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIsLeafByCode"), code);
	}

	@Override
	public TradeCategory queryTagsById(Integer id) {
		return (TradeCategory)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTagsById"),id);
	}
	
	@Override
	public TradeCategory getCategoryByCode(String code){
		return (TradeCategory)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getCategoryByCode"), code);
	}

	@Override
	public String queryNameByCode(String code) {
		
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByCode"),code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCategory> queryBroCategoryByCode(String code, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code",code);
		map.put("size",size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBroCategoryByCode"),map);
	}

	@Override
	public TradeCategory queryTradeCategoryById(Integer id) {
		
		return (TradeCategory)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTradeCategoryById"),id);
	}
}