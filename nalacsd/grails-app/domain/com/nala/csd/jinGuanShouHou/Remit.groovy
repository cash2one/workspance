package com.nala.csd.jinGuanShouHou

import com.nala.csd.Hero
import java.util.Date;

/**
 * 金冠售后表
 * 打款操作
 * @author Kenny
 *
 */
class Remit {

    static constraints = {
		remitDate nullable:true
		remitNum nullable:true
		remitOperator nullable:true
        remitNumProduct nullable:true
        remitNumPostage nullable: true
        remitNumCommission nullable: true
        remitStatusEnum nullable: true
    }
	
	/**
	 * 打款时间
	 */
	Date remitDate
	
	/**
	 * 打款金额
     * 单位：分
	 */
	Integer remitNum
	
	/**
	 * 打款操作人
	 */
	Hero remitOperator

    /**
     * 打款金额（产品）
     */
    Integer remitNumProduct

    /**
     * 打款金额（邮费）
     * 单位：分
     */
    Integer remitNumPostage

    /**
     * 打款手续费
     */
    Integer remitNumCommission

    /**
     * 打款优先级
     */
    RemitStatusEnum remitStatusEnum
	
	
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

/**
 * 打款优先级
 */
enum RemitStatusEnum {
    normal(0, '普通'), emergency(1, '紧急')

    private final Integer code;
    private final String description;

    RemitStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RemitStatusEnum getByCode(def code){
        def res
        code = Integer.valueOf(code)
        RemitStatusEnum.values().each { status->
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