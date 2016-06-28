package com.nala.csd.jinGuanShouHou

import com.nala.csd.Hero

class Coupon {

    static constraints = {
        couponNum nullable: true
        couponOperator nullable: true
        couponDate nullable: true
    }

    /**
     * 优惠券金额
     */
    Integer couponNum

    /**
     * 赠送操作人
     */
    Hero couponOperator

    /**
     * 赠送时间
     */
    Date couponDate

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
