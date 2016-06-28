/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-15
 */
package com.ast.ast1949.util;

/**
 * 常用类别的枚举值
 * 
 * @author yuyonghui
 * 
 */
public enum EnumUtils {

	/**
	 * 名企访谈
	 */
	NEWS_INTERVIEW("1"),
	/**
	 * 再生技术
	 */
	NEWS_TECH("2"),
	/**
	 * 技术热点
	 */
	NEWS_HOT_TECH("3"),
	/**
	 * 商务热点
	 */
	NEWS_HOT_BIZ("4"),
	/**
	 * 热门资讯
	 */
	NEWS_HOT_NEWS("5"),

	/**
	 * 废金属
	 */
	PRODUCTS_METAL("1000"),
	/**
	 * 废塑料
	 */
	PRODUCTS_PLASTIC("1001"),
	/**
	 * 废纸
	 */
	PRODUCTS_PAPER("1002"),
	/**
	 * 废纺织和皮革
	 */
	PRODUCTS_TEXTILE_LEATHER("1003"),
	/**
	 * 废旧轮胎和橡胶
	 */
	PRODUCTS_TIRE_RUBBER("1004"),
	/**
	 * 废旧设备和交通工具
	 */

	PRODUCTS_EQUIPMENT_VEHICLES("1005"),
	/**
	 * 废旧电子和电脑设备
	 */
	PRODUCTS_COMPUTER("1006"),
	/**
	 * 废旧玻璃和废木制品
	 */

	PRODUCTS_GLASS_WOOD("1007"),
	/**
     *   供求信息
     */
	PRODUCTS_CODE("10351001"),
	/**
	 * 报价
	 */
	PRICE_CODE("10351003"),
	
	;
	private final String value;

	private EnumUtils(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
