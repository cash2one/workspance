package com.ast.feiliao91.service.trade;

public interface TradeService {

	/**
	 * 用自己的账户余额支付
	 * orderNo 订单编号
	 * payPassword 支付密码
	 */
	public Integer payBySumOfMoney(String orderNo, String payPassword);
	
	/**
	 * 交易成功，卖家账户到款
	 * orderNo 订单编号
	 */
	public Integer paySucForSell(String orderNo);

}
