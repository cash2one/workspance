package com.ast.ast1949.paychannel;

/*
 业务类型 
 1001 充值
 1002 提现
 1003 支付
 1004 退款
 */
public class TransType {
	public static String Recharge = "1001"; // 充值 RechargeBILL
	public static String Transfer_Out_Real = "1002";// 实时提现 WithDrawBILL
	public static String Direct_Pay = "1003"; // 即时到帐支付 PayBill
	public static String Direct_Refund = "1004"; // 退款 PayBill
	public static String ADJUST_ACC = "1006"; // 人工调账
}
