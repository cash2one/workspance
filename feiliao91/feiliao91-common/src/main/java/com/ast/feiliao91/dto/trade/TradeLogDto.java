package com.ast.feiliao91.dto.trade;


import net.sf.json.JSONObject;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.TradeLog;

public class TradeLogDto {
	private Integer companyId;
	private TradeLog tradeLog;
	private Goods goods;
	private Orders orders;
	private CompanyInfo sellCompany;// 卖家公司信息
	private JSONObject detailJson; //订单表中的详细信息
	private CashAdvance cashAdvance;//提现类
	private Integer type; //流水表中的type （1为订单流水，2为充值流水，3为提现流水，4为保证金流水）
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public void setTradeLog(TradeLog tradeLog) {
		this.tradeLog = tradeLog;
	}
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	public CompanyInfo getSellCompany() {
		return sellCompany;
	}

	public void setSellCompany(CompanyInfo sellCompany) {
		this.sellCompany = sellCompany;
	}
	
	public JSONObject getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(JSONObject detailJson) {
		this.detailJson = detailJson;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public CashAdvance getCashAdvance() {
		return cashAdvance;
	}

	public void setCashAdvance(CashAdvance cashAdvance) {
		this.cashAdvance = cashAdvance;
	}
}
