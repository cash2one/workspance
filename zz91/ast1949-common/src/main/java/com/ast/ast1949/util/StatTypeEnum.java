/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-13 下午04:52:13
 */
package com.ast.ast1949.util;

/**
 * 网站通用数据统计（只统计访问量的板块）的类型
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public enum StatTypeEnum {

	/**
	 * 报价统计
	 */
	stat_price("0"),
	/**
	 * 广告统计
	 */
	stat_ad("1");
	private final String value;

	private StatTypeEnum(String value) {
		this.value = value;
	}

	/**
	 * 获取对应的值
	 *
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 根据值获取表示含义的字符串
	 *
	 * @param value
	 * @return
	 */
	public static String getString(String value) {
		for (StatTypeEnum e : StatTypeEnum.values()) {
			if (value.equals(e.getValue())) {
				return e.toString();
			}
		}
		return null;
	}
}
