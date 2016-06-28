package com.nala.csd

import java.util.Date
import com.nala.csd.jinGuanShouHou.Refund
import com.nala.csd.jinGuanShouHou.Remit
import com.nala.csd.jinGuanShouHou.Resend
import com.nala.csd.jinGuanShouHou.ReturnGoods
import com.nala.csd.jinGuanShouHou.QuestionReason;

/**
 * NALA天猫/B2C/拍拍店售后登记表
 * @author Kenny
 *
 */
class TmallShouhou {

	static constraints = {
		userId nullable:true
        tradeId(nullable: true)
		questionStatus nullable:true
        questionReason nullable: true
		wrongReason nullable:true
		noSolveReason nullable:true
		wuliuNo nullable:true
		moneyRecordType nullable:true
		solveStatus nullable:true
		solveType nullable:true
		itemDetail nullable:true
        refund nullable:true
        remit nullable:true
        resend nullable:true
        returnGoods nullable:true
	}

	/**
	 * 创建时间
	 */
	Date dateCreated

	/**
	 * 登记人
	 */
	Hero createCS

	/**
	 * 顾客id
	 */
	String userId

    /**
     * 订单编号
     */
    String tradeId

	/**
	 * 问题类型
	 */
	QuestionType questionStatus

    /**
     * 问题原因
     */
    QuestionReason questionReason

	/**
	 * 商品明细
	 */
	String itemDetail

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
	
	def wrongReasonForList() {
		if (wrongReason){
			if (wrongReason.length() > 12){
				return wrongReason.substring(0, 12) + "..."
			}else{
				return wrongReason
			}
		}
	}

	/**
	 * 完成进度
	 */
	SolveStatusEnum solveStatus

	/**
	 * 未完成说明
	 */
	String noSolveReason
	
	def noSolveReasonForList() {
		if (noSolveReason){
			if (noSolveReason.length() > 12){
				return noSolveReason.substring(0, 12) + "..."
			}else{
				return noSolveReason
			}
		}
	}

	/**
	 * 退回单号
	 */
	String wuliuNo
	
	/**
	 * 店铺
	 */
    Store store	

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

}
