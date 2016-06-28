package com.ast1949.shebei.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.BaseDao;
import com.ast1949.shebei.dao.ProductsDao;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;
/**
 * 
 * @author 陈庆林
 * 2012-7-24 下午4:09:02
 */

@Component("productsDao")
public class ProductsDaoImol extends BaseDao implements ProductsDao {
	final static String SQL_PREFIX="products";
	@Override
	public Integer insertProducts(Products product) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertProducts"), product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> queryProductsByType(Integer cid, Short type,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("type", type);
		map.put("size", size);
		return (List<Products>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryProductsByType"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> queryProductsByCidAndType(String categoryCode,
			Integer cid, Short type, PageDto<Products> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		map.put("cid", cid);
		map.put("type", type);
		map.put("page", page);
		return (List<Products>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryProductsByCidAndType"),map);
	}

	@Override
	public Integer queryProductsByCidAndTypeCount(String categoryCode,
			Integer cid, Short type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		map.put("cid", cid);
		map.put("type", type);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryProductsByCidAndTypeCount"),map);
	}

	@Override
	public Products queryProductbyId(Integer id) {
		// TODO Auto-generated method stub
		return (Products)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryProductbyId"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> queryOtherProducts(Integer cid, String categoryCode,
			Short type, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		map.put("size", size);
		map.put("type", type);
		map.put("cid", cid);
		return (List<Products>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryOtherProducts"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> queryRelatedProducts(String categoryCode, Integer cid,
			Short type, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		map.put("size", size);
		map.put("type", type);
		map.put("cid", cid);
		return (List<Products>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRelatedProducts"),map);
	}

	@Override
	public Date queryMaxGmtShow() {
		return (Date)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxGmtShow"));
	}

}
