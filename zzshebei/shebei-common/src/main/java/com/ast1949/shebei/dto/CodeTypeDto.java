package com.ast1949.shebei.dto;

import java.io.Serializable;
import java.util.List;

import com.ast1949.shebei.domain.CategoryProducts;

@SuppressWarnings("serial")
public class CodeTypeDto implements Serializable {
	
	private CategoryProducts  categoryProducts;
	private List<CategoryProducts> listProducts;
	public CodeTypeDto(){
		
	}
	public CodeTypeDto(CategoryProducts categoryProducts,
			List<CategoryProducts> listProducts) {
		super();
		this.categoryProducts = categoryProducts;
		this.listProducts = listProducts;
	}
	public CategoryProducts getCategoryProducts() {
		return categoryProducts;
	}
	public void setCategoryProducts(CategoryProducts categoryProducts) {
		this.categoryProducts = categoryProducts;
	}
	public List<CategoryProducts> getListProducts() {
		return listProducts;
	}
	public void setListProducts(List<CategoryProducts> listProducts) {
		this.listProducts = listProducts;
	}
}
