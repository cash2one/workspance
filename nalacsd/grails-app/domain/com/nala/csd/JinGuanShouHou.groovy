package com.nala.csd

import com.nala.csd.jinGuanShouHou.QuestionReason
import com.nala.csd.jinGuanShouHou.Refund;
import com.nala.csd.jinGuanShouHou.Remit;
import com.nala.csd.jinGuanShouHou.Resend;
import com.nala.csd.jinGuanShouHou.ReturnGoods
import com.nala.csd.jinGuanShouHou.Coupon
import com.nala.csd.jinGuanShouHou.ReturnGoodsConfirm;

/**
 * 金冠店售后表
 * @author Kenny
 *
 */
class JinGuanShouHou {

    static hasMany = [confirms:ReturnGoodsConfirm]

    static constraints = {
		contactTime nullable:true
		faqiTime nullable:true
		solveTime nullable:true
		zhidingTime nullable:true	
		lianxiJiange nullable:true
		jiejueJiange nullable:true
		contactStatus nullable:true
		contactTimes nullable:true
		moneyRecordType nullable:true
		solveStatus nullable:true
		solveType nullable:true
		solveCS nullable:true
		itemDetail nullable:true
		itemCode nullable:true
		itemTitle nullable:true
		sku nullable:true
		returnGoods nullable:true
		remit nullable:true
		refund nullable:true
		resend nullable:true
		userId  nullable:true
		tradeId nullable:true
		wuliuNo nullable:true
		questionReason nullable:true
        coupon nullable: true
        tag nullable: true
        tel nullable: true

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
	 * 发起时间
	 */
	String faqiTime
	
	/**
	 * 顾客id
	 */
	String userId
	
	/**
	 * 订单编号
	 */
	String tradeId
	
	/**
	 * 联系优先级
	 */
	ContactStatusEnum contactStatus
	
	/**
	 * 联系电话
	 */
	String tel
	
	/**
	 * 指定时间
	 */
	String zhidingTime
	
	/**
	 * 问题类型
	 */
	QuestionType questionStatus
	
	/**
	 * 问题原因
	 */
	QuestionReason questionReason
	
	/**
	 * 产品简称
	 */
	String itemTitle
	
	/**
	 * 产品代码
	 */
	String itemCode
	
	/**
	 * 产品规格
	 */
	String sku
	
	/**
	 * 产品数量
	 */
	int itemNum
	
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
	 * 联系次数
	 */
	ContactTimesEnum contactTimes
	
//	/**
//	 * 处理方式
//	 */
//	SolveTypeEnum solveType


    /**
     * 处理方式
     */
    JinTmallSolveType solveType
	
	/**
	 * 货款操作记录
	 */
	MoneyRecordTypeEnum moneyRecordType
	
	/**
	 * 信息错误说明
	 */
	String wrongReason
	
	/**
	 * 完成进度
	 */
	SolveStatusEnum solveStatus
	
	/**
	 * 未完成说明
	 */
	String noSolveReason
	
	/**
	 * 退回单号
	 */
	String wuliuNo
	
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
	 * 商品明细
	 */
	String itemDetail
	
	/**
	 * 退款操作
	 */
	Refund refund
	
	/**
	 * 打款操作
	 */
	Remit remit
	
	/**
	 * 补发信息
	 */
	Resend resend
	
	/**
	 * 退货信息
	 */
	ReturnGoods returnGoods

    /**
     * 赠送优惠券信息
     */
    Coupon coupon

    /**
     * 中差评标示
     */
    String tag

    /**
     * 列表页显示商品明细
     * @return
	 */



	def itemDeailForList() {
		if (itemDetail){
			if (itemDetail.length() > 12){
				return itemDetail.substring(0, 12) + "..."
			}else{
				return itemDetail
			}
		}
	}
}



enum SolveStatusEnum {
	no(0, '未完成'), yes(1, '已完成')

	private final Integer code;
	private final String description;

	SolveStatusEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public static SolveStatusEnum getByCode(def code){
		def res
		code = Integer.valueOf(code)
		SolveStatusEnum.values().each { status->
			if(status.code==code){
				res= status
			}
		}
		return res
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}

enum MoneyRecordTypeEnum {
	none(0, '其他'), 
	dengji(5, '已登记'), 
	bufa(1, '已补发'), 
	tuikuan(2, '已退款'), 
	dakuan(3, '已打款'), 
	wrong(4, '信息有误'), 
	returnDakuan(6, '收到退货可打款'), 
	returnTuikuan(7, '收到退货可退款'), 
	returnDakuanBufa(8, '收到退货可打款可补发'), 
	returnBufa(9, '收到退货可补发'),
	returnWait(10, '收到退货待确认'),
    bufadakuan(11, '已补发已打款'),
    bufatuikuan(12, '已补发已退款'),
    xiadanfufa(13, '已下单可补发')
	
	private final Integer code;
	private final String description;

	MoneyRecordTypeEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public static MoneyRecordTypeEnum getByCode(def code){
		def res
		code = Integer.valueOf(code)
		MoneyRecordTypeEnum.values().each { status->
			if(status.code==code){
				res= status
			}
		}
		return res
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}

enum ContactStatusEnum {
	normal(0, '普通'), immediately(1, '立刻'), time(2, '指定时间'), noUseForTel(3, '无需电联'), none(4, '其他')
	
	private final Integer code;
	private final String description;

	ContactStatusEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public static ContactStatusEnum getByCode(def code){
		def res
		code = Integer.valueOf(code)
		ContactStatusEnum.values().each { status->
			if(status.code==code){
				res= status
			}
		}
		return res
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}



enum ContactTimesEnum {
	no(0, '无需联系'), one(1, '一次联系'), two(2, '两次联系'), three(3, '三次联系'), four(4, '四次联系'), five(5, '五次及以上')
	
	private final Integer code;
	private final String description;

	ContactTimesEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public static ContactTimesEnum getByCode(def code){
		def res
		code = Integer.valueOf(code)
		ContactTimesEnum.values().each { status->
			if(status.code==code){
				res= status
			}
		}
		return res
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}

