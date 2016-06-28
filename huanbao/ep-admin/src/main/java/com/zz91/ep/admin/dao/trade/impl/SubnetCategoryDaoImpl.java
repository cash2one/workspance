package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.SubnetCategoryDao;
import com.zz91.ep.domain.trade.SubnetCategory;

@Component("subnetCategoryDao")
public class SubnetCategoryDaoImpl extends BaseDao implements SubnetCategoryDao {

	final static String SQL_PREFIX = "subnetCategory";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParentId"), parentId);
	}
	
	@Override
	public Integer queryChildCountByParentId(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryChildCountByParentId"), id);
	}
	
	@Override
	public Integer insertSubnetCategory(SubnetCategory category) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertSubnetCategory"), category);
	}

	@Override
	public Integer updateSubnetCategory(SubnetCategory category) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSubnetCategory"), category);
	}

	@Override
	public Integer deleteCategoryByIdOrParentId(Integer id, Integer parentId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("parentId", parentId);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCategoryByIdOrParentId"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetCategory> queryAllCategory() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllCategory"));
	}
}
