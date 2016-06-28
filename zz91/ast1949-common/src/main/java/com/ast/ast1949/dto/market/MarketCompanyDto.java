package com.ast.ast1949.dto.market;

import java.util.Date;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.market.Market;

public class MarketCompanyDto {
	private Company company;
	private Market market;
	
	private String account;//登录帐号
	
	private Date regTime;//入驻市场时间

	
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
	

}
