/**
 * @author shiqp
 * @date 2015-07-15
 */
package com.ast.ast1949.dto.market;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.products.ProductsDO;

public class MarketDto {
	private Company company;
	private Market market;
	private ProductsDO products;
	private String picAddress;

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public ProductsDO getProducts() {
		return products;
	}

	public void setProducts(ProductsDO products) {
		this.products = products;
	}

}
