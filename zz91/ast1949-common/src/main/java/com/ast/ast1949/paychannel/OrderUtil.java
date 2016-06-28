package com.ast.ast1949.paychannel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {

	public static void main(String[] args) {
		System.out.println(orderSeq());
	}
	
	/**
	 * 订单序号 yyMMddHHmmss+Random(2) 
	 */
	public static String orderSeq() {
		// 当前时间 yyyyMMddHHmmss
		String currTime = getCurrTime();
		// 6位时间
		String strTime = currTime.substring(2, currTime.length());
		// 四位随机数
		String strRandom = buildRandom(2) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		return strReq;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
}
