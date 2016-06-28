/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 by liulei.
 */
package com.ast.ast1949.service.products.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.persist.products.ProductsSeriesDAO;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.util.Assert;

/**
 * @author liulei
 * 
 */
@Component("productsSeriesService")
public class ProductsSeriesServiceImpl implements ProductsSeriesService {

	@Autowired
	private ProductsSeriesDAO productsSeriesDAO;

//	public List<ProductsSeriesDO> queryProductsSeries(ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO, "Object productsSeriesDO must not be null");
//		List<ProductsSeriesDO> list = productsSeriesDAO.queryProductsSeries(productsSeriesDO);
//		return list;
//	}

	

//	public Integer deleteProductsSeriesContacts(Integer id) {
//		Assert.notNull(id, "The id must not be null");
//		return productsSeriesDAO.deleteProductsSeriesContacts(id);
//	}

//	public Integer insertProductsSeries(ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO, "Object productsSeriesDO must not be null");
//		return productsSeriesDAO.insertProductsSeries(productsSeriesDO);
//	}

//	public Integer insertProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO) {
//		Assert
//				.notNull(productsSeriesContactsDO,
//						"Object productsSeriesContactsDO must not be null");
//		return productsSeriesDAO.insertProductsSeriesContacts(productsSeriesContactsDO);
//	}

//	public Map<ProductsSeriesDO, List<ProductsSeriesDTO>> queryHadSubSeries(
//			ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO.getCompanyId(), "The companyId must not be null");
//		Map<ProductsSeriesDO, List<ProductsSeriesDTO>> map = new LinkedHashMap<ProductsSeriesDO, List<ProductsSeriesDTO>>();
//		List<ProductsSeriesDTO> dtoList = new ArrayList<ProductsSeriesDTO>();
//		List<ProductsSeriesDO> list = productsSeriesDAO.queryProductsSeries(productsSeriesDO);
//		for (ProductsSeriesDO o : list) {
//			//根据ProductsSeriesDO中id,companyId,排序seriesOrder查询已分类别
//			dtoList = productsSeriesDAO.queryHadSubSeries(o);
//			map.put(o, dtoList);
//		}
//		return map;
//	}

//	@Override
//	public PageDto<ProductsSeriesDTO> queryProductInSeriesListBySeriesId(
//			Integer userSeriesId, PageDto<ProductsSeriesDTO> pager) {
//		Assert.notNull(userSeriesId, "The userSeries id must not be null");
//		return productsSeriesDAO.queryProductInSeriesListBySeriesId(userSeriesId, pager);
//	}
	
//	@Override
//	public PageDto<ProductsSeriesDTO> queryProductNotInSeriesListByCompanyId(
//			Integer companyId, PageDto<ProductsSeriesDTO> pager) {
//		Assert.notNull(companyId, "The companyId must not be null");
//		return productsSeriesDAO.queryProductNotInSeriesListByCompanyId(companyId, pager);
//	}
	

//	public List<ProductsSeriesDTO> queryNoHadSubSeries(Integer companyId) {
//		Assert.notNull(companyId, "The companyId must not be null");
//		return productsSeriesDAO.queryNoHadSubSeries(companyId);
//	}

	

	

//	public Integer updateProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO) {
//		Assert.notNull(productsSeriesContactsDO.getId(), "The id must not be null");
//		return productsSeriesDAO.updateProductsSeriesContacts(productsSeriesContactsDO);
//	}

//	public Integer updateProductsSeriesName(ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO.getId(), "The id must not be null");
//		return productsSeriesDAO.updateProductsSeriesName(productsSeriesDO);
//	}

//	public Integer editProductsSeriesContactsStatus(String ids, Integer productsSeriesId) {
//		Integer m = null;
//		ProductsSeriesContactsDO productsSeriesContactsDO = new ProductsSeriesContactsDO();
//		productsSeriesContactsDO.setProductsSeriesId(productsSeriesId);
//		if (ids != null) {
//			String[] str = ids.split(",");
//			for (int i = 0; i < str.length; i++) {
//				productsSeriesContactsDO.setProductsId(Integer.valueOf(str[i]));
//				ProductsSeriesContactsDO pscDO = productsSeriesDAO
//						.selectProductSeriesContactsByProductsId(Integer.valueOf(str[i]));
//				// 当根据ProductsId查询ProductsSeriesContactsDO信息为null,表示要插入数据，否则修改数据
//				if (pscDO == null) {
//					m = productsSeriesDAO.insertProductsSeriesContacts(productsSeriesContactsDO);
//				} else {
//					productsSeriesContactsDO.setId(pscDO.getId());
//					m = productsSeriesDAO.updateProductsSeriesContacts(productsSeriesContactsDO);
//				}
//			}
//		}
//		return m;
//	}

//	public Integer updateProductsSeriesOrder(ProductsSeriesDO productsSeriesDO) {
//		Assert.notNull(productsSeriesDO.getId(), "The id can not be null");
//		return productsSeriesDAO.updateProductsSeriesOrder(productsSeriesDO);
//	}

	/*****************up code old*****************/
	
	@Override
	public Integer createSeries(ProductsSeriesDO group) {
		
		Integer count=productsSeriesDAO.countSeriesOfCompany(group.getCompanyId());
		if(count>20){
			return 0;
		}
		
		return productsSeriesDAO.insertProductsSeries(group);
	}

	@Override
	public Integer createSeriesContacts(Integer groupId, Integer[] productId) {
		
		if(groupId==null){
			return null;
		}
		
		for(Integer pid:productId){
			productsSeriesDAO.deleteSeriesContacts(null, pid);
			productsSeriesDAO.insertSeriesContacts(groupId, pid);
		}
		return 1;
	}

	@Override
	public Integer deleteSeriesContacts(Integer groupId, Integer[] productId) {
		
		for(Integer pid:productId){
			productsSeriesDAO.deleteSeriesContacts(groupId, pid);
			
		}
		
		return 1;
	}

	@Override
	public List<ProductsSeriesDO> querySeriesOfCompany(Integer companyId) {
		
		return productsSeriesDAO.querySeriesOfCompany(companyId);
	}
	
	public ProductsSeriesDO queryProductsSeriesById(Integer id) {
		Assert.notNull(id, "The id must not be null");
		return productsSeriesDAO.queryProductsSeriesById(id);
	}
	
	public Integer updateProductsSeries(ProductsSeriesDO productsSeriesDO) {
		Assert.notNull(productsSeriesDO.getId(), "The id must not be null");
		return productsSeriesDAO.updateProductsSeries(productsSeriesDO);
	}
	
	public Integer deleteProductsSeries(Integer id) {
		Assert.notNull(id, "The id must not be null");
		productsSeriesDAO.deleteSeriesContacts(id, null);
		return productsSeriesDAO.deleteProductsSeries(id);
	}

	@Override
	public ProductsSeriesDO queryProductsSeriesByProudctId(Integer productId) {
		Assert.notNull(productId, "The id must not be null");
		return productsSeriesDAO.queryProductsSeriesByProudctId(productId);
	}
}
