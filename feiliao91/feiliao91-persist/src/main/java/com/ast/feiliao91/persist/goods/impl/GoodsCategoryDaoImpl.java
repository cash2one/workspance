/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.GoodsCategoryDao;

@Component("goodsCategoryDao")
public class GoodsCategoryDaoImpl extends BaseDaoSupport implements GoodsCategoryDao {
	final static String SQL_PREFIX="goodsCategory";
	
	@Override
	public Integer insertGoodsCategory(GoodsCategory goodCategory) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertGoodsCategory"), goodCategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCategory> queryCategoryByParentCode(String parentCode) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCategoryByParentCode"), parentCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCategory> queryGoodsCategoryByKeyword(String keyword, Integer size, Integer length) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("size", size);
		map.put("length", length);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryGoodsCategoryByKeyword"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCategory> queryAllGoodsCategory() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllGoodsCategory"));
	}
	
	public GoodsCategory queryCategoryByCode(String code){
		return (GoodsCategory) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCategoryByCode"), code);
	}
}
