package com.ast1949.shebei.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.ProductsDao;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.ProductsService;

@Component("productsService")
public class ProductsServiceImpl implements ProductsService {
	
	@Resource
	private ProductsDao productsDao;
	
	@Override
	public Integer createProducts(Products product) {
		
		return null;
	}

	@Override
	public List<Products> queryProductsByType(Integer cid, Short type,
			Integer size) {
		// TODO Auto-generated method stub
		return productsDao.queryProductsByType(cid, type, size);
	}

	@Override
	public PageDto<Products> pageProducts(String categoryCode, Integer cid,
			Short type, PageDto<Products> page) {
		page.setTotals(productsDao.queryProductsByCidAndTypeCount(categoryCode, cid, type));
		page.setRecords(productsDao.queryProductsByCidAndType(categoryCode, cid, type, page));
		return page;
	}

	@Override
	public Products queryProductbyId(Integer id) {
		// TODO Auto-generated method stub
		return productsDao.queryProductbyId(id);
	}

	@Override
	public List<Products> queryOtherProducts(Integer cid, String categoryCode,
			Short type, Integer size) {
		// TODO Auto-generated method stub
		return productsDao.queryOtherProducts(cid, categoryCode, type, size);
	}

	@Override
	public List<Products> queryRelatedProducts(String categoryCode,
			Integer cid, Short type, Integer size) {
		// TODO Auto-generated method stub
		return productsDao.queryRelatedProducts(categoryCode, cid, type, size);
	}

	@Override
	public Date queryMaxGmtShow() {
		// TODO Auto-generated method stub
		return productsDao.queryMaxGmtShow();
	}

}
