package com.ast.ast1949.service.products.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.persist.products.ProductsExpireDao;
import com.ast.ast1949.service.products.ProductsExpireService;
@Component("productsExpireService")
public class ProductsExpireServiceImpl implements ProductsExpireService{
	@Resource
	private ProductsExpireDao productsExpireDao;

	@Override
	public Integer insert(ProductsExpire productsExpire) {
		Integer i=0;
		if (productsExpire!=null) {
			i=productsExpireDao.insert(productsExpire);
		}
		return i;
	}

	@Override
	public ProductsExpire queryByProductsId(Integer productsId) {
		ProductsExpire productsExpire=new ProductsExpire();
		if (productsId!=null&&productsId.intValue()>0) {
			productsExpire=productsExpireDao.queryByProductsId(productsId);
		}
		return productsExpire;
	}

	@Override
	public Integer UpdateDayById(Integer id, Integer day) {
		Integer i=0;
		if (id!=null&& id.intValue()>0&& day!=null) {
			i=productsExpireDao.updateDayById(id, day);
		}
		return i;
	}
	
	

}
