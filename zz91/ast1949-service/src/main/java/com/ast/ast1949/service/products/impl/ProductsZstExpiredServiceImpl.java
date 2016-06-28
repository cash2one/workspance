package com.ast.ast1949.service.products.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.products.ProductsZstExpiredDAO;
import com.ast.ast1949.service.products.ProductsZstExpiredService;
import com.zz91.util.Assert;

@Component("productsZstExpiredService")
public class ProductsZstExpiredServiceImpl implements ProductsZstExpiredService{

	@Resource
	private ProductsZstExpiredDAO productsZstExpiredDAO;
	@Override
	public Integer deleteByProductId(Integer productId) {
		Assert.notNull(productId, "productId can not be null");
		return productsZstExpiredDAO.deleteByProductId(productId);
	}
}
