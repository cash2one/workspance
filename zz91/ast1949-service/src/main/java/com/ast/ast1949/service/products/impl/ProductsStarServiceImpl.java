/**
 * @author kongsj
 * @date 2015年4月20日
 * 
 */
package com.ast.ast1949.service.products.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsStar;
import com.ast.ast1949.persist.products.ProductsStarDao;
import com.ast.ast1949.service.products.ProductsStarService;

@Component("productsStarService")
public class ProductsStarServiceImpl implements ProductsStarService{

	@Resource
	private ProductsStarDao productsStarDao;
	
	@Override
	public Integer insert(Integer productsId, Integer score) {
		if (productsId==null||productsId==0) {
			return 0;
		}
		if (score==null||score<0) {
			score = 0;
		}
		if (score >100) {
			score = 100;
		}
		ProductsStar productsStar = productsStarDao.queryByProductsId(productsId); 
		// 如果数据存在。。则执行更新语句
		if (productsStar!=null) {
			return 	updateByProductsId(productsStar.getProductsId(), score);
		}
		// 数据不存在 执行insert语句
		productsStar = new ProductsStar();
		productsStar.setProductsId(productsId);
		productsStar.setScore(score);
		return productsStarDao.insert(productsStar);
		
	}

	@Override
	public ProductsStar queryById(Integer id) {
		if (id==null||id == 0) {
			return null;
		}
		return productsStarDao.queryById(id);
	}

	@Override
	public ProductsStar queryByProductsId(Integer productsId) {
		if (productsId==null||productsId==0) {
			return null;
		}
		return productsStarDao.queryByProductsId(productsId);
	}

	@Override
	public Integer updateByProductsId(Integer productsId, Integer score) {
		if (productsId==null||productsId==0) {
			return 0;
		}
		if (score<0) {
			score = 0;
		}
		if (score >100) {
			score = 100;
		}
		return 	productsStarDao.updateByProductsId(productsId, score);
	}

}
