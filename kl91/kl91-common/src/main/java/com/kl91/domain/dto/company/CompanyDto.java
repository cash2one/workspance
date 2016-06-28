package com.kl91.domain.dto.company;

import com.kl91.domain.DomainSupport;
import com.kl91.domain.company.Company;
import com.kl91.domain.products.Products;

public class CompanyDto extends DomainSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6127509169584494730L;
	
	private Company company;
	private Products products;

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
