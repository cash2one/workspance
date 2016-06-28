package com.kl91.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.products.Products;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.products.ProductsDao;

@Component("productsDao")
public class ProductsDaoImpl extends BaseDaoSupport implements ProductsDao {

	private static String SQL_PREFIX = "products";

	public Integer insert(Products product) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertProducts"), product);
	}

	public Integer update(Products product) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateProducts"), product);
	}

	public Products queryById(Integer id) {
		return (Products) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}

	public Integer delete(Integer id) {
		return (Integer) getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteProducts"), id);
	}

	public Integer updateProductsIsNoPub(Integer[] ids,Integer publishFlag) {	
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("ids", ids);
		root.put("publishFlag", publishFlag);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateProductsIsNoPub"),root);
	}
	public Integer updateProductsIsNoPub(Integer[] ids) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateProductsIsNoPub"), ids);
	}

	@SuppressWarnings("unchecked")
	public List<Products> queryProducts(Products products,
			PageDto<Products> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("products", products);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryProducts"), root);
	}


	public Integer queryProductsCount(ProductsSearchDto searchDto,PageDto<ProductsDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("searchDto", searchDto);
		root.put("page", page);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProductsCount"),root);
	}
		
	public Integer queryProductsCount(Products products) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("products", products);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryProductsCount"), root);
	}

	public Integer refreshProductsByIds(Integer[] ids) {
		return getSqlMapClientTemplate().update(
				"products.refreshProductsByIds", ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsForList(ProductsSearchDto searchDto, Integer size,PageDto<ProductsDto> page) {
		if (size == null) {
			size = 5;
		}
		Map<String , Object> root=new HashMap<String, Object>();
		root.put("searchDto", searchDto);
		root.put("size", size);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryProducts"),root);
	}
	@SuppressWarnings("unchecked")
	public List<Products> queryProductsForList(Products products, Integer size,
			PageDto<Products> page) {
		if (size == null) {
			size = 5;
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("products", products);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryProductsForList"), root);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> queryProductsByCompanyId(Integer companyId,
			Products products, PageDto<Products> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("page", page);
		root.put("products", products);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryProductsByCompanyId"), root);
	}

	@Override
	public ProductsDto queryProductsAndCompanyById(Integer id) {

		return (ProductsDto) getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryProductsAndCompanyById"), id);
	}

	@Override
	public Integer countProductsIsPassByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countProductsIsPassByCompanyId"),
				companyId);
	}

	@Override
	public Integer deleteMost(Integer[] ids,Integer deletedFlag) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("ids", ids);
		root.put("deletedFlag", deletedFlag);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "batchDelete"),root);
	}

	public Integer countProducts(Integer companyId, String productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("productType", productType);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countProducts"), map);
	}

	public Integer updatePub(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updatePub"), id);
	}

}
