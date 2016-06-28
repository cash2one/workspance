package com.ast.ast1949.service.products.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ast.ast1949.domain.products.ProductsViewHistory;
import com.ast.ast1949.persist.products.ProductsViewHistoryDao;
import com.ast.ast1949.service.products.ProductsViewHistoryService;
import com.zz91.util.Assert;

/**
 * @Author:kongsj
 * @Date:2011-12-27
 */
@Service
public class ProductsViewHistoryServiceImpl implements ProductsViewHistoryService {
	@Autowired
	private ProductsViewHistoryDao productsViewHistoryDao;

	@Override
	public Integer create(ProductsViewHistory productsViewHistory) {
		Assert.notNull(productsViewHistory, "the obj productsViewHistory can not be null");
		return productsViewHistoryDao.insert(productsViewHistory);
	}

	@Override
	public List<ProductsViewHistory> queryHistory(String cookieKey, Integer size) {
		if (size == null) {
			size = 10;
		}
		if(size>100){
			size=100;
		}
		return productsViewHistoryDao.queryHistory(cookieKey, size);
	}

	@Override
	public Integer updateCompanyIdByCookieKey(Integer companyId,
			String cookieKey) {
		
		return productsViewHistoryDao.updateCompanyIdByCookieKey(cookieKey,
				companyId);
	}

	@Override
	public String queryKeyByCompanyId(Integer companyId) {
		
		return productsViewHistoryDao.queryKeyByCompanyId(companyId);
	}

	@Override
	public Integer updateGmtLastView(Integer id) {
		
		return productsViewHistoryDao.updateGmtLastView(id);
	}

}
