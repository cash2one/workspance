package com.nala.csd.jinGuanShouHou

import java.util.Date;

import com.nala.csd.Hero

/**
 * 金冠售后表
 * 补发信息
 * @author Kenny
 *
 */
class Resend {

    static constraints = {
		postageNum nullable:true
		date nullable:true
		wuliuNo nullable:true
		goodsDetail nullable:true
		address nullable:true
		resender nullable:true

    }
	
	/**
	 * 补发日期
	 */
	Date date
	
	/**
	 * 补发单号
	 */
	String wuliuNo
	
	/**
	 * 补发邮费
     * 单位：分
	 */
	Integer postageNum
	
	/**
	 * 补发产品明细
	 */
	String goodsDetail
	
	/**
	 * 补发地址
	 */
	String address
	
	/**
	 * 补发人
	 */
	Hero resender
	
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
