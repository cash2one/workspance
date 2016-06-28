package com.ast.feiliao91.dto.trade;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.trade.CashAdvance;

public class CashAdvanceDto {
	private CompanyAccount companyAccount;
	private CashAdvance cashAdvance;

	public CompanyAccount getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(CompanyAccount companyAccount) {
		this.companyAccount = companyAccount;
	}
	
	public CashAdvance getCashAdvance() {
		return cashAdvance;
	}

	public void setCashAdvance(CashAdvance cashAdvance) {
		this.cashAdvance = cashAdvance;
	}
}
