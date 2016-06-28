package com.ast.ast1949.dto.market;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.MarketSubscribe;

public class MarketSubscribeDto {
	private MarketSubscribe subscribe;
	private Company company;
	private CompanyAccount account;
	private String keywordsTags;
	private String marketTags;

	public MarketSubscribe getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(MarketSubscribe subscribe) {
		this.subscribe = subscribe;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyAccount getAccount() {
		return account;
	}

	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public String getKeywordsTags() {
		return keywordsTags;
	}

	public void setKeywordsTags(String keywordsTags) {
		this.keywordsTags = keywordsTags;
	}

	public String getMarketTags() {
		return marketTags;
	}

	public void setMarketTags(String marketTags) {
		this.marketTags = marketTags;
	}
	
}
