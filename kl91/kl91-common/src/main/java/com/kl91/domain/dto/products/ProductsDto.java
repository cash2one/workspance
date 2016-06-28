package com.kl91.domain.dto.products;

import com.kl91.domain.DomainSupport;
import com.kl91.domain.company.Company;
import com.kl91.domain.products.Products;

public class ProductsDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6399277547565451798L;
	private Products products;
	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	
}
