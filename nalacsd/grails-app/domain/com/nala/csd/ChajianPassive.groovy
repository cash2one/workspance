package com.nala.csd

import java.util.Date;


/**
 * 查件被动表
 * @author Kenny
 *
 */
class ChajianPassive {

    static constraints = {
		userId nullable:true
		chajianCode nullable:true
		logistics nullable:true
		results nullable:true
		solveCS nullable:true
		contactTime nullable:true
		solveTime nullable:true
		updateTime nullable:true
		mobile nullable:true
		notifyMode nullable:true
		followCS nullable:true
		lianxiJiange nullable:true
		jiejueJiange nullable:true
		store nullable:true
		contactStatus nullable:true
		tradeId nullable:true
    }
	
	/**
	 * 创建时间
	 */
	Date dateCreated
	
	/**
	 * 顾客id
	 */
	String userId
	
	/**
	 * 订单编号
	 */
	String tradeId

	/**
	 * 查件代码
	 */
	ChajianCode chajianCode
	
	/**
	 * 物流单号
	 */
	String logistics
	
	/**
	 * 查询备注
	 */
	String results
	
	/**
     * 创建人
	 */
	Hero createCS
	
	/**
	 * 解决人、处理人
	 */
	Hero solveCS
	
	/**
	 * 联系时间
	 */
	String contactTime
   
	/**
	 * 解决时间
	 */
	String solveTime
	
	/**
	 * 跟踪时间
	 */
	Date updateTime
	
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
	 * 是否签收
	 */
	boolean signUp = false
	
	/**
	 * 创建时间到联系时间的间隔
	 * 单位：分钟
	 */
	Long lianxiJiange
   
    /**
	 * 联系时间到解决时间的间隔
	 * 单位：分钟
	 */
	Long jiejueJiange
	
	/**
	 * 店铺
	 */
	Store store
	
	/**
	* 联系优先级
	*/
	ContactStatusEnum contactStatus
	
	def beforeUpdate = {
		updateTime = new Date()
	}
	
	/**
	 * 列表页显示物流单号
	 * @return
	 */
	def logisticsForList(){
		if (logistics){
			if (logistics.length() > 20){
				return logistics.substring(0, 20) + "..."
			}else{
				return logistics
			}
		}
	} 
}
