package com.nala.csd.jinGuanShouHou

import java.util.Date;

import com.nala.csd.Hero

/**
 * 金冠售后表
 * 退款操作
 * @author Kenny
 *
 */
class Refund {

    static constraints = {
		refundDate nullable:true
		refundNum nullable:true
		refundOperator nullable:true
        refundNumProduct nullable: true
        refundNumPostage nullable: true
    }
	
	/**
	 * 退款时间
	 */
	Date refundDate
	
	/**
	 * 退款金额
	 */
	Integer refundNum
	
	/**
	 * 退款操作人
	 */
	Hero refundOperator

    /**
     * 退款金额（产品）
     */
    Integer refundNumProduct

    /**
     * 退款金额（邮费）
     */
    Integer refundNumPostage
	
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
