/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.dto.products;

import java.util.List;

import com.ast.ast1949.domain.products.CategoryProductsDO;

/**
 * @author yuyonghui
 *
 */
public class CategoryProductsDTO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private CategoryProductsDO categoryProductsDO;
	private List<CategoryProductsDTO> child;

	public CategoryProductsDO getCategoryProductsDO() {
		return categoryProductsDO;
	}
	public void setCategoryProductsDO(CategoryProductsDO categoryProductsDO) {
		this.categoryProductsDO = categoryProductsDO;
	}
	public void setChild(List<CategoryProductsDTO> child) {
		this.child = child;
	}
	public List<CategoryProductsDTO> getChild() {
		return child;
	}
}
