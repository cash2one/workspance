package com.ast.ast1949.service.products.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAutoCheck;
import com.ast.ast1949.persist.products.ProductsAutoCheckDao;
import com.ast.ast1949.service.products.ProductsAutoCheckService;
import com.zz91.util.lang.StringUtils;

@Component("productsAutoCheckService")
public class ProductsAutoCheckServiceImpl implements ProductsAutoCheckService{

	
	@Resource
	private ProductsAutoCheckDao productsAutoCheckDao;
	
	@Override
	public Integer insert(Integer productId) {
		ProductsAutoCheck obj = new ProductsAutoCheck();
		obj.setProductId(productId);
		obj.setCheckStatus(ProductsAutoCheckService.WAIT_CHECK);
		return productsAutoCheckDao.insert(obj);
	}

	@Override
	public List<ProductsAutoCheck> queryCheckBySize(Integer size) {
		if(size==null){
			return null;
		}
		if(size >100){
			size =100;
		}
		return productsAutoCheckDao.queryCheckBySize(size);
	}

	@Override
	public Integer updateByStatus(Integer id, String checkStatus) {
		if(id==null){
			return 0;
		}
		if(StringUtils.isEmpty(checkStatus)){
			return 0;
		}
		return productsAutoCheckDao.updateByStatus(id, checkStatus);
	}

	@Override
	public ProductsAutoCheck queryById(Integer id) {
		return productsAutoCheckDao.queryById(id);
	}

}
