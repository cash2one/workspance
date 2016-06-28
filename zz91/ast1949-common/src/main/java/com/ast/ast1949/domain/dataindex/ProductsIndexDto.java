package com.ast.ast1949.domain.dataindex;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsIndexDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1058542431046407518L;
	private ProductsIndex productsIndex;
	private String companyName;

	public ProductsIndex getProductsIndex() {
		return productsIndex;
	}

	public void setProductsIndex(ProductsIndex productsIndex) {
		this.productsIndex = productsIndex;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
