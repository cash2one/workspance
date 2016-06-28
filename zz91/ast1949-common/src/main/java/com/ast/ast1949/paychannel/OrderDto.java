package com.ast.ast1949.paychannel;

import com.ast.ast1949.domain.sample.OrderBill;

public class OrderDto {
	private String astoAccount; // asto帐号，如支付宝，财付通帐号
	private String orderSeq;// 订单
	private String orderSubject;// 订单名称
	private String totalAmount;// 订单金额
	private String virtualAmount;// 订单金额
	private String orderDes;// 订单描述
	private String showUrl;// 商品展示地址

	// 接受回执URL
	private String notifyUrl;
	// 返回跳转URL
	private String returnUrl;
	// 收款方
	private String spname;
	// 商户号
	private String partner;
	// 密钥
	private String key;
	
	private String channelType;
	
	private OrderBill orderBill ;
	

	public OrderDto() {
	}
	
	public OrderDto(String notifyUrl, String returnUrl, String spname, String partner, String key) {
		super();
		this.notifyUrl = notifyUrl;
		this.returnUrl = returnUrl;
		this.spname = spname;
		this.partner = partner;
		this.key = key;
	}
	
	public OrderDto(OrderBill orderBill) {
		super();
		this.orderBill = orderBill;
	}

	public String getAstoAccount() {
		return astoAccount;
	}

	public void setAstoAccount(String astoAccount) {
		this.astoAccount = astoAccount;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderSubject() {
		return orderSubject;
	}

	public void setOrderSubject(String orderSubject) {
		this.orderSubject = orderSubject;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public OrderBill getOrderBill() {
		return orderBill;
	}

	public String getVirtualAmount() {
		return virtualAmount;
	}

	public void setVirtualAmount(String virtualAmount) {
		this.virtualAmount = virtualAmount;
	}

	public void setOrderBill(OrderBill orderBill) {
		this.orderBill = orderBill;
	}
}
