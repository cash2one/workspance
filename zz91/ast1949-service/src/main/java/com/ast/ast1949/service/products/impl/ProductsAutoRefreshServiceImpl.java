package com.ast.ast1949.service.products.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAutoRefresh;
import com.ast.ast1949.persist.products.ProductsAutoRefreshDAO;
import com.ast.ast1949.service.products.ProductsAutoRefreshService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;

@Component("productsAutoRefreshService")
public class ProductsAutoRefreshServiceImpl implements
		ProductsAutoRefreshService {
	static {
		dateMap.put("1", "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31");
		dateMap.put("2", "2,4,6,8,10,12,14,16,18,20,22,24,26,28,30");
		dateMap.put("3", "3,6,9,12,15,18,21,24,27,30");
		dateMap.put("7", "1,8,15,22,29");
		dateMap.put("30", "1");
	}
	@Resource
	private ProductsAutoRefreshDAO productsAutoRefreshDAO;
	
	
	@Override
	public ProductsAutoRefresh queryByCid(Integer cid) {
		Assert.notNull(cid, "the cid must not be null!");
		return productsAutoRefreshDAO.queryByCompanyId(cid);
	}

	@Override
	public Integer updateRefreshDate(String refreshDate,String refreshRate, Integer id) {
		Assert.notNull(id, "the id must not be null!");
		Assert.notNull(refreshDate, "the refreshDate must not be null");
		if (StringUtils.isNotEmpty(refreshRate)) {
			if (StringUtils.isNotEmpty(refreshDate)) {
				refreshDate = refreshDate.trim();
				refreshDate +=",";
			}
			refreshDate += dateMap.get(refreshRate);
		}
		return productsAutoRefreshDAO.update(refreshDate, id);
	}

	@Override
	public Integer insertRefreshDate(ProductsAutoRefresh productsAutoRefresh) {
		Assert.notNull(productsAutoRefresh, "the productsAutoRefresh must not be null!");
		if (StringUtils.isNotEmpty(productsAutoRefresh.getRefreshRate())) {
			if (StringUtils.isNotEmpty(productsAutoRefresh.getRefreshDate())) {
				productsAutoRefresh.setRefreshDate( "," + productsAutoRefresh.getRefreshDate().trim());
			}
			productsAutoRefresh.setRefreshDate(dateMap.get(productsAutoRefresh.getRefreshRate()) + productsAutoRefresh.getRefreshDate());
		}
		return productsAutoRefreshDAO.insert(productsAutoRefresh);
	}
}
