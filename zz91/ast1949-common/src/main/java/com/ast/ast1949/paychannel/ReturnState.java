package com.ast.ast1949.paychannel;

public class ReturnState {
	private String returnState; // 通讯状态值---超时T、成功S、失败F
	
	private String orderSeq; // 交易订单号
	private Double relTranAmount;// 交易金额
	private String channelRecvSn; // 交易返回流水
	private String channelRetCode; // 交易返回代码 (成功:0000)
	private String channelRetMsg;// 交易返回信息

	private String channelType;// 渠道类型

	private String buyerAcc;
	private String sellerAcc; 
	
	public ReturnState() {
		super();
	}

	public ReturnState(String orderSeq, Double relTranAmount, String channelRetCode) {
		super();
		this.orderSeq = orderSeq;
		this.relTranAmount = relTranAmount;
		this.channelRetCode = channelRetCode;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Double getRelTranAmount() {
		return relTranAmount;
	}

	public void setRelTranAmount(Double relTranAmount) {
		this.relTranAmount = relTranAmount;
	}

	public String getReturnState() {
		return returnState;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public String getChannelRecvSn() {
		return channelRecvSn;
	}

	public void setChannelRecvSn(String channelRecvSn) {
		this.channelRecvSn = channelRecvSn;
	}

	public String getChannelRetCode() {
		return channelRetCode;
	}

	public void setChannelRetCode(String channelRetCode) {
		this.channelRetCode = channelRetCode;
	}

	public String getChannelRetMsg() {
		return channelRetMsg;
	}

	public void setChannelRetMsg(String channelRetMsg) {
		this.channelRetMsg = channelRetMsg;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getBuyerAcc() {
		return buyerAcc;
	}

	public void setBuyerAcc(String buyerAcc) {
		this.buyerAcc = buyerAcc;
	}

	public String getSellerAcc() {
		return sellerAcc;
	}

	public void setSellerAcc(String sellerAcc) {
		this.sellerAcc = sellerAcc;
	}
}
