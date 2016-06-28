package com.ast.ast1949.service.products.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsHide;
import com.ast.ast1949.persist.products.ProductsHideDao;
import com.ast.ast1949.service.products.ProductsHideService;
@Component("productsHideService")
public class ProductsHideServiceImpl implements ProductsHideService{
	@Resource
	private ProductsHideDao productsHideDao;
	@Override
	public Integer insert(ProductsHide productsHide) {
		Integer i=0;
		if (productsHide!=null) {
			i=productsHideDao.insert(productsHide);
		}
		return i;
	}

	@Override
	public ProductsHide queryByProductId(Integer productId) {
		ProductsHide productsHide=new ProductsHide();
		if (productId!=null&&productId.intValue()>0) {
			productsHide=productsHideDao.queryByProductId(productId);
		}
		return productsHide;
	}

	@Override
	public Integer delete(Integer productId) {
		Integer i=0;
		if (productId!=null&&productId.intValue()>0) {
			i=productsHideDao.delete(productId);
		}
		return i;
	}
	@Override
	public Integer countByCompanyId(Integer companyId){
		Integer i=0;
		if (companyId!=null&&companyId.intValue()>0) {
			i=productsHideDao.countByCompanyId(companyId);
		}
		return i;
	}
}
