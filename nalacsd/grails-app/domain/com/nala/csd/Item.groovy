package com.nala.csd

/**
 * 商品
 * @author Kenny
 *
 */
class Item implements Comparable{

    static constraints = {
		sku nullable:true
		code nullable:true
		num nullable:true		
    }
	
	int compareTo(obj) {
		id.compareTo(obj.id)
	}
	
	/**
	 * 商品名称
	 */
	String name
	
	/**
	 * 商品sku
	 */
	String sku
	
	/**
	 * 商家编码
	 */
	String code
	
	/**
	 * 购买数量
	 */
	Integer num
	
}
