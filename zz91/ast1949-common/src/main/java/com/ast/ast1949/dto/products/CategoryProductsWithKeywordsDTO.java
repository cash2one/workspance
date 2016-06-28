/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-4 下午05:05:30
 */
package com.ast.ast1949.dto.products;

import com.ast.ast1949.domain.products.CategoryProductsDO;

/**
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class CategoryProductsWithKeywordsDTO implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CategoryProductsDO categoryProductsDO;
	private String keywords;

	public CategoryProductsDO getCategoryProductsDO() {
		return categoryProductsDO;
	}
	public void setCategoryProductsDO(CategoryProductsDO categoryProductsDO) {
		this.categoryProductsDO = categoryProductsDO;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
