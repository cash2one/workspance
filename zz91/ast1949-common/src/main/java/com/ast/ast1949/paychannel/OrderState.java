package com.ast.ast1949.paychannel;

/*
 交易状态 00:待支付 01:支付中 03:支付成功 04:支付失败 11:卖方确认发货 12:买方确认收货 13:交易完成 14:退款已申请 15:卖家同意退货 16:买家退货发货  17:已退货  20:未支付关闭
 */
public class OrderState {
	/**
	 * 00:待支付
	 */
	public static String STATE_00 = "00";
	/**
	 * 01:支付中
	 */
	public static String STATE_01 = "01";
	/**
	 * 02:未确认
	 */
	public static String STATE_02 = "02";
	/**
	 *  03:支付成功
	 */
	public static String STATE_03 = "03";
	/**
	 * 04:支付失败
	 */
	public static String STATE_04 = "04";
	/**
	 * 06:买家提醒发货
	 */
	public static String STATE_06 = "06";
	/**
	 *  11:卖方确认发货
	 */
	public static String STATE_11 = "11";
	/**
	 * 12:买方确认收货
	 */
	public static String STATE_12 = "12";
	/**
	 *  13:交易完成
	 */
	public static String STATE_13 = "13";
	/**
	 * 14:退款已申请
	 */
	public static String STATE_14 = "14";
	/**
	 * 15:卖家同意退货
	 */
	public static String STATE_15 = "15";
	/**
	 * 16:买家退货发货
	 */
	public static String STATE_16 = "16";
	/**
	 * 17:已退货
	 */
	public static String STATE_17= "17";
	
	/**
	 * 18:卖家不同意退货
	 */
	public static String STATE_18= "18";
	
	/**
	 * 20:未交易关闭
	 */
	public static String STATE_20 = "20";
}
