package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.SubnetCategoryDao;
import com.zz91.ep.domain.trade.SubnetCategory;

@Component("subnetCategoryDao")
public class SubnetCategoryDaoImpl extends BaseDao implements SubnetCategoryDao {

	final static String SQL_PREFIX="subnetCategory";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId,Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentId", parentId);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParentId"), root);
	}

	@Override
	public SubnetCategory queryCategoryByCode(String code) {
		return (SubnetCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCategoryByCode"), code);
	}

	@Override
	public SubnetCategory querySubCateById(Integer id) {
		return (SubnetCategory) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySubCateById"), id);
	}
	
}
