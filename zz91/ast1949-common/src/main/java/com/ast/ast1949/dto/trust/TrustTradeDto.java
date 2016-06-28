package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.domain.trust.TrustTrade;

public class TrustTradeDto {
	private Company company;
	private Company toCompany;
	private TrustTrade trade;
	private TrustDealer dealer;
	private TrustBuy buy;
	private Integer hasPic;//有无交易凭证

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getToCompany() {
		return toCompany;
	}

	public void setToCompany(Company toCompany) {
		this.toCompany = toCompany;
	}

	public TrustTrade getTrade() {
		return trade;
	}

	public void setTrade(TrustTrade trade) {
		this.trade = trade;
	}

	public TrustDealer getDealer() {
		return dealer;
	}

	public void setDealer(TrustDealer dealer) {
		this.dealer = dealer;
	}

	public TrustBuy getBuy() {
		return buy;
	}

	public void setBuy(TrustBuy buy) {
		this.buy = buy;
	}
	
	public Integer getHasPic() {
		return hasPic;
	}

	public void setHasPic(Integer hasPic) {
		this.hasPic = hasPic;
	}
}
