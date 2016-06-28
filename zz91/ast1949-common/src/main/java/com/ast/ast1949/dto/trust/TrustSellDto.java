/**
 * @author shiqp
 * @date 2015-05-14
 */
package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.domain.trust.TrustRelateDealer;
import com.ast.ast1949.domain.trust.TrustRelateSell;
import com.ast.ast1949.domain.trust.TrustSell;

public class TrustSellDto {
	private TrustSell sell;
	private TrustRelateSell relateSell;
	private TrustBuy buy;
	private TrustRelateDealer relateDealer;
	private TrustDealer dealer;
	private Company company;
	private String categoryName;
	private Company toCompany;
	private String toCompanyContact;
	private String toCompanyMobile;

	public TrustBuy getBuy() {
		return buy;
	}

	public void setBuy(TrustBuy buy) {
		this.buy = buy;
	}

	public TrustRelateDealer getRelateDealer() {
		return relateDealer;
	}

	public void setRelateDealer(TrustRelateDealer relateDealer) {
		this.relateDealer = relateDealer;
	}

	public TrustDealer getDealer() {
		return dealer;
	}

	public void setDealer(TrustDealer dealer) {
		this.dealer = dealer;
	}

	public TrustSell getSell() {
		return sell;
	}

	public void setSell(TrustSell sell) {
		this.sell = sell;
	}

	public TrustRelateSell getRelateSell() {
		return relateSell;
	}

	public void setRelateSell(TrustRelateSell relateSell) {
		this.relateSell = relateSell;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Company getToCompany() {
		return toCompany;
	}

	public void setToCompany(Company toCompany) {
		this.toCompany = toCompany;
	}

	public String getToCompanyContact() {
		return toCompanyContact;
	}

	public void setToCompanyContact(String toCompanyContact) {
		this.toCompanyContact = toCompanyContact;
	}

	public String getToCompanyMobile() {
		return toCompanyMobile;
	}

	public void setToCompanyMobile(String toCompanyMobile) {
		this.toCompanyMobile = toCompanyMobile;
	}

}
