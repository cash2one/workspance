package com.ast.feiliao91.dto.goods;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;

public class OrderReturnDto {
	private OrderReturn orderReturn;
	private Orders orders;
	private Goods goods;
	private CompanyInfo companyInfo;// 卖家信息
	private Float price;// 交易金额

	public OrderReturn getOrderReturn() {
		return orderReturn;
	}

	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}


}
