/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 by liulei.
 */
package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsSeriesDAO;

/**
 * @author liulei
 *
 */
@Component("productsSeriesDAO")
public class ProductsSeriesDAOImpl extends BaseDaoSupport implements ProductsSeriesDAO {

//	private static String sqlPreFix="productSeries";
	
	

//	public Integer deleteProductsSeriesContacts(Integer id) {
//		return getSqlMapClientTemplate().delete("productSeries.deleteProductsSeriesContacts", id);
//	}

	

//	public Integer insertProductsSeriesContacts(
//			ProductsSeriesContactsDO productsSeriesContactsDO) {
//		return Integer.valueOf(getSqlMapClientTemplate()
//                .insert("productSeries.insertProductsSeriesContacts", productsSeriesContactsDO).toString());
//	}

//	@SuppressWarnings("unchecked")
//	public List<ProductsSeriesDTO> queryHadSubSeries(ProductsSeriesDO productsSeriesDO) {
//		return getSqlMapClientTemplate()
//			.queryForList("productSeries.queryHadSubSeries", productsSeriesDO);
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<ProductsSeriesContactsDO> queryNoContactsIdBySeriesId() {
//		return getSqlMapClientTemplate().queryForList("productSeries.queryNoContactsIdBySeriesId");
//	}

//	@SuppressWarnings("unchecked")
//	public List<ProductsSeriesDTO> queryNoHadSubSeries(Integer companyId) {
//		return getSqlMapClientTemplate()
//		.queryForList("productSeries.queryNoHadSubSeries", companyId);
//	}

//	@Override
//	public PageDto<ProductsSeriesDTO> queryProductInSeriesListBySeriesId(
//			Integer userSeriesId, PageDto<ProductsSeriesDTO> pager) {
//		Map paramMap=new HashMap();
//		paramMap.put("userSeriesId", userSeriesId);
//		paramMap.put("page", pager);
//		List<ProductsSeriesDTO> psList=getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductInSeriesListByProductsSeries"), paramMap);
//		pager.setTotalRecords((Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductInSeriesListByProductsSeriesCount"), userSeriesId));
//		pager.setRecords(psList);
//		return pager;
//	}
	

//	@Override
//	public PageDto<ProductsSeriesDTO> queryProductNotInSeriesListByCompanyId(Integer companyId,
//			PageDto<ProductsSeriesDTO> pager) {
//		Map paramMap=new HashMap();
//		paramMap.put("companyId", companyId);
//		paramMap.put("page", pager);
//		List<ProductsSeriesDTO> psList=getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductNotInSeriesListByCompanyId"), paramMap);
//		pager.setTotalRecords((Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductNotInSeriesListByCompanyIdCount"), companyId));
//		pager.setRecords(psList);
//		return pager;
//	}

//	@SuppressWarnings("unchecked")
//	public List<ProductsSeriesDO> queryProductsSeries(ProductsSeriesDO productsSeriesDO) {
//		return getSqlMapClientTemplate()
//			.queryForList("productSeries.queryProductsSeries", productsSeriesDO);
//	}

	

//	public ProductsSeriesContactsDO selectProductSeriesContactsByProductsId(
//			Integer productsId) {
//		Assert.notNull(productsId, "The productsId can not be null");
//		return (ProductsSeriesContactsDO)getSqlMapClientTemplate()
//			.queryForObject("productSeries.selectProductSeriesContactsByProductsId", productsId);
//	}

	

//	public Integer updateProductsSeriesContacts(
//			ProductsSeriesContactsDO productsSeriesContactsDO) {
//		return getSqlMapClientTemplate()
//		.update("productSeries.updateProductsSeriesContacts",productsSeriesContactsDO);
//	}

//	public Integer updateProductsSeriesName(ProductsSeriesDO productsSeriesDO) {
//		return getSqlMapClientTemplate().update("productSeries.updateProductsSeriesName", productsSeriesDO);
//	}

//	public Integer updateProductsSeriesOrder(ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO.getId(), "The id can not be null");
//		return getSqlMapClientTemplate().update("productSeries.updateProductsSeriesOrder", productsSeriesDO);
//	}
	
	/***************old***********/

	final static String SQL_PREFIX="productSeries";
	
	@Override
	public Integer countSeriesOfCompany(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countSeriesOfCompany"), companyId);
	}

	@Override
	public Integer deleteSeriesContacts(Integer groupId, Integer productId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("groupId", groupId);
		root.put("productId", productId);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteSeriesContacts"), root);
	}

	@Override
	public Integer insertSeriesContacts(Integer groupId, Integer productId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("groupId", groupId);
		root.put("productId", productId);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertSeriesContacts"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsSeriesDO> querySeriesOfCompany(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySeriesOfCompany"), companyId);
	}
	
	@Override
	public Integer insertProductsSeries(ProductsSeriesDO productsSeriesDO) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertProductsSeries"), productsSeriesDO);
	}
	
	@Override
	public ProductsSeriesDO queryProductsSeriesById(Integer id) {
		return (ProductsSeriesDO)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProductsSeriesById"), id);
	}
	@Override
	public Integer updateProductsSeries(ProductsSeriesDO productsSeriesDO) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateProductsSeries"),productsSeriesDO);
	}
	
	@Override
	public Integer deleteProductsSeries(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix("productSeries","deleteProductsSeries"), id);
	}

	@Override
	public ProductsSeriesDO queryProductsSeriesByProudctId(Integer productId) {
		return (ProductsSeriesDO)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryProductsSeriesByProudctId"), productId);
	}

	@Override
	public Integer querySeriesContacts(Integer sid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "querySeriesContacts"), sid);
	}
}
