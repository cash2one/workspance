package com.nala.csd

import java.util.Date;

/**
 * B2C顾客咨询登记表
 * @author Kenny
 *
 */
class B2CConsult {

	static constraints = {
		questionSource nullable:true
		userId nullable:true
		phone nullable:true
		name nullable:true
		questionStatus nullable:true
		tradeId nullable:true
		noSolveReason nullable:true
		b2CConsultResult nullable:true
	}

	/**
	 * 创建时间
	 */
	Date dateCreated

	/**
	 * 创建人
	 */
	Hero createCS

	/**
	 * 咨询方式
	 */
	QuestionSource questionSource

	/**
	 * 顾客id
	 */
	String userId

	/**
	 * 手机号
	 */
	String phone

	/**
	 * 顾客姓名
	 */
	String name

	/**
	 * 问题类型
	 */
	QuestionType questionStatus

	/**
	 * 订单编号
	 */
	String tradeId

	/**
	 * 未完成说明
	 */
	String noSolveReason

	/**
	 * 列表页未完成说明
	 * @return
	 */
	def noSolveReasonForList() {
		if (noSolveReason){
			int maxLength = 25
			if (noSolveReason.length() > maxLength){
				return noSolveReason.substring(0, maxLength) + "..."
			}else{
				return noSolveReason
			}
		}
	}

	/**
	 * 处理 结果
	 */
	B2CConsultResult b2CConsultResult
}
