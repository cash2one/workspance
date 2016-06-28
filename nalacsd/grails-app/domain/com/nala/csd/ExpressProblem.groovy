package com.nala.csd

/**
 * 快递问题
 * 由快递公司发过来的文件上传所得
 * @author Kenny
 *
 */
class ExpressProblem {

    static constraints = {
        logisticsID unique: true
    }
	
	/**
	 * 所属快递公司
	 */
	Express express
	
	/**
	 * 物流单号 
	 */
	String logisticsID
	
	/**
	 * 问题类型
	 */
	String problemType
	
	/**
	 * 问题描述
	 */
	String description
	
}
