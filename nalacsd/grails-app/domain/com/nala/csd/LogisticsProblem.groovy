package com.nala.csd

import java.util.Date;
import com.nala.common.utils.DateUtils

/**
 * 快递问题件
 * @author Kenny
 *
 */
class LogisticsProblem {

	static constraints = {
		store nullable:true
		userId nullable:true
		solveCS nullable:true
		solveTime nullable:true
		remark nullable:true
		solveType nullable:true
		mobile nullable:true
		notifyMode nullable:true
		followCS nullable:true
		followTime nullable:true
		jiejueJiange nullable:true
		results nullable:true
		receiveInfo nullable:true
        sendGoodsDate nullable: true
	}

	/**
	 * 创建时间
	 */
	Date dateCreated

	/**
	 * 所属店铺
	 */
	Store store

	/**
	 * 顾客id
	 */
	String userId

	/**
	 * 问题件
	 */
	ExpressProblem expressProblem

	/**
	 * 解决时间
	 */
	String solveTime

	/**
	 * 备注
	 */
	String remark

	/**
	 * 处理人
	 */
	Hero solveCS

	/**
	 * 处理方式
	 */
	String solveType

	/**
	 * 手机号码
	 */
	String mobile

	/**
	 * 通知方式
	 */
	NotifyMode notifyMode

	/**
	 * 是否跟踪
	 */
	boolean followUp = false

	/**
	 * 跟踪人
	 */
	Hero followCS

	/**
	 * 跟踪时间
	 */
	String followTime

	/**
	 * 是否签收
	 */
	boolean signUp = false

	/**
	 * 创建时间到解决时间的间隔
	 * 单位：分钟
	 */
	Long jiejueJiange

	/**
	 * 处理结果
	 */
	String results

	/**
	 * 收件信息
	 */
	String receiveInfo

    /**
     * 发货日期
     */
    Date sendGoodsDate

	/**
	 * 列表页显示处理方式
	 * @return
	 */
	def solveTypeForList() {
		if (solveType){
			if (solveType.length() > 15){
				return solveType.substring(0, 15) + "..."
			}else{
				return solveType
			}
		}
	}

	def beforeUpdate = {
		Date curDate = new Date()
		String curDateStr = DateUtils.parseToWebFormat2(curDate)
		followTime = curDateStr
	}
}
