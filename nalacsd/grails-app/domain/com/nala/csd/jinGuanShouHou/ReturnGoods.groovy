package com.nala.csd.jinGuanShouHou

import java.util.Date;

/**
 * 金冠售后表
 * 退货信息
 * @author Kenny
 *
 */
class ReturnGoods {

    static constraints = {
		postageNum nullable:true
		detail nullable:true
		returnDate nullable:true
		wuliuNo nullable:true
		postageRole nullable:true
    }
	
	/**
	 * 收到退货明细
	 */
	String detail
	
	/**
	 * 收到退货日期
	 */
	Date returnDate
	
	/**
	 * 退货快递和单号
	 */
	String wuliuNo
	
	/**
	 * 是否使用
	 */
	boolean used = false
	
	/**
	 * 邮费承担方
	 */
	String postageRole
	
	/**
	 * 邮费金额
	 * 单位：分
	 */
	Integer postageNum
	
	Date dateCreated
	Date lastUpdated
	
	def beforeInsert = {
		def now =  new Date()
		dateCreated = now
		lastUpdated = now
	}
	
	def beforeUpdate = {
		lastUpdated = new Date()
	}
}
