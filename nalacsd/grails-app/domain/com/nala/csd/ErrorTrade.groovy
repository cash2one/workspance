package com.nala.csd

import java.util.Date;

/**
 * 异常单
 * @author Kenny
 *
 */
class ErrorTrade {

	static constraints = {
		dateCreated nullable:true
		store nullable:true
		userId nullable:true
		tradeId nullable:true
		reason nullable:true
		errorReason nullable:true
		content nullable:true
		createCS nullable:true
		solveCS nullable:true
		solveTime nullable:true
		jiejueJiange nullable:true
		buyerRemarks nullable:true
		sellerRemarks nullable:true
		solveType nullable:true
		itemSKUs nullable:true
		contactTimes nullable:true
		receiver nullable:true
		address nullable:true
		phone nullable:true
		solveStatus nullable:true
        dealPriority nullable: true

	}
	
	/**
	 * 可以有很多商品
	 */
	SortedSet items
	static hasMany = [items: Item]

	/**
	 * 创建时间
	 */
	Date dateCreated

	/**
	 * 店铺
	 */
	Store store

	/**
	 * 顾客id
	 */
	String userId

	/**
	 * 订单id
	 */
	String tradeId

	/**
	 * 原因
	 * 已经废弃！用errorReason取代！
	 */
	String reason

	/**
	 * 异常原因	
	 */
	ErrorReasonForTrade errorReason

	/**
	 * 描述
	 */
	String content

	/**
	 * 创建人
	 */
	Hero createCS
	
	/**
     * 联系次数
	 */
    ContactTimesEnum contactTimes

	/**
	 * 解决人、处理人
	 */
	Hero solveCS
	
	/**
	 * 是否处理
	 * 舍弃
	 */
	boolean finish = false
	
	/**
 	 * 完成进度
	 */
    SolveStatusEnum solveStatus
	
	/**
 	 * 解决时间
	 */
	String solveTime
	
	/**
	 * 创建时间到解决时间的间隔
	 * 单位：分钟
	 */
    Long jiejueJiange
	
	/**
	 * 买家备注
	 */
	String buyerRemarks
	
	/**
	 * 卖家备注
	 */
	String sellerRemarks
	
	/**
	 * 处理方式
	 */
	SolveType solveType
	
	/**
	 * 商品sku
	 * 搜索用
	 */
	String itemSKUs
	
	/**
	 * 收货人
	 */
	String receiver
	
	/**
	 * 收货地址
	 */
	String address
	
	/**
	 * 电话
	 */
	String phone

    /**
     * 处理有限级
     */
    DealPriority dealPriority


	/**
	 * 列表页面描述
	 * @return
	 */
	def contentForList() {
		if (content){
			int maxLength = 12
			if (content.length() > maxLength){
				return content.substring(0, maxLength) + "..."
			}else{
				return content
			}
		}
	}
	
	/**
	 * 列表页订单编号
	 * @return
	 */
	def tradeIdForList() {
		if (tradeId){
			int maxLength = 25
			if (tradeId.length() > maxLength){
				return tradeId.substring(0, maxLength) + "..."
			}else{
				return tradeId
			}
		}
	}
	
	/**
	 * 列表页顾客Id
	 * @return
	 */
	def userIdForList() {
		if (userId){
			int maxLength = 10
			if (userId.length() > maxLength){
				return userId.substring(0, maxLength) + "..."
			}else{
				return userId
			}
		}
	}
	
	/**
	 * 列表页买家留言
	 * @return
	 */
	def buyerRemarksForList() {
		if (buyerRemarks){
			int maxLength = 10
			if (buyerRemarks.length() > maxLength){
				return buyerRemarks.substring(0, maxLength) + "..."
			}else{
				return buyerRemarks
			}
		}
	}
	
	/**
	 * 列表页卖家留言
	 * @return
	 */
	def sellerRemarksForList() {
		if (sellerRemarks){
			int maxLength = 10
			if (sellerRemarks.length() > maxLength){
				return sellerRemarks.substring(0, maxLength) + "..."
			}else{
				return sellerRemarks
			}
		}
	}

}
