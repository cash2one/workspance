package com.ast1949.shebei.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.BaseDao;
import com.ast1949.shebei.dao.CategoryProductsDao;
import com.ast1949.shebei.domain.CategoryProducts;

@Component("categoryProductsDao")
public class CategoryProductsDaoImpl extends BaseDao implements CategoryProductsDao {
	final static String SQL_PREFIX = "categoryProducts";
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryProducts> queryAllCategorys(Short flag,String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("code", code);
		return (List<CategoryProducts>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllCategorys"),map);
	}
	
	@Override
	public CategoryProducts queryCtypeByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		return (CategoryProducts)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCtypeByCode"),map);
	}

}
