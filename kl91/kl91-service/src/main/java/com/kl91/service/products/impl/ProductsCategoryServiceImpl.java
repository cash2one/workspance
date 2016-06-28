package com.kl91.service.products.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.products.ProductsCategory;
import com.kl91.persist.products.ProductsCategoryDao;
import com.kl91.service.products.ProductsCategoryService;

@Component("productsCategoryService")
public class ProductsCategoryServiceImpl implements ProductsCategoryService{
	
	@Resource
	private ProductsCategoryDao productsCategoryDao;

	@Override
	public Integer createProductsCategory(ProductsCategory productsCategory,
			String parentCode) {
		String code=queryMaxCodeBypreCode(parentCode);
		productsCategory.setCode(code);
		return productsCategoryDao.insertCategoryProducts(productsCategory);
	}

	@Override
	public Integer deleteProductsCategory(Integer id) {
		
		return productsCategoryDao.delete(id);
	}

	@Override
	public Integer editProductCategory(ProductsCategory productsCategory) {
		
		return productsCategoryDao.updatecategoryProducts(productsCategory);
	}

	@Override
	public List<ProductsCategory> queryByCode(String code) {
		
		return productsCategoryDao.queryByCode(code);
	}

	@Override
	public ProductsCategory queryById(Integer id) {
		
		return productsCategoryDao.queryById(id);
	}

	@Override
	public String queryMaxCodeBypreCode(String preCode) {
		
		String code =productsCategoryDao.queryMaxCodeBypreCode(preCode);
		if (code == null || code.length() == 0) {
			if(preCode==null){
				preCode="";
			}
			code = preCode + "1000";
		}// Code值的长度为四时10000001+1
		else if (code.length() == 4) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		} else if (code.length() == 8) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		}// string类型 存值有限 必须 先 截取后四位+1 然后赋值
		else {
			String code3 = code.substring(code.length() - 4, code.length());
			String code4 = code.substring(0, code.length() - 4);
			code = code4 + String.valueOf(Integer.valueOf(code3) + 1);
		}
		return code;
	}

	@Override
	public List<ProductsCategory> queryAllCategoryProducts() {
		return productsCategoryDao.queryAllCategoryProducts();
	}

}
