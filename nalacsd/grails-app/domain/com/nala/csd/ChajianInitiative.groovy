package com.nala.csd

/**
 * 主动查件
 */
class ChajianInitiative {

    static constraints = {
        userId nullable: true
        chajianCode nullable: true
        logistics nullable: true
        mobile nullable: true
        solveType nullable: true
        solveCS nullable: true
        followCS nullable: true
        followTime nullable: true
        contactTime nullable: true
        notifyMode nullable: true
        contactInterval nullable: true
        followInterval nullable: true
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
     * 店铺
     */
    Store store

    /**
     * 查件代码
     */
    ChajianCode chajianCode

    /**
     * 物流单号
     */
    String logistics

    /**
     * 手机号码
     */
    String mobile

    /**
     * 处理方式
     */
    String solveType

    /**
     * 处理人
     */
    Hero solveCS

    /**
     * 跟踪人
     */
    Hero followCS

    /**
     * 跟踪时间
     */
    Date followTime

    def beforeUpdate = {
        followTime = new Date()
    }

    /**
     * 联系时间
     */
    Date contactTime


    /**
     * 通知方式
     */
    NotifyMode notifyMode

    /**
     * 是否跟踪
     */
    boolean followUp = false

    /**
     * 是否签收
     */
    boolean signUp = false

    /**
     * 创建时间到联系时间的间隔
     * 单位：分钟
     */
    Long contactInterval

    /**
     * 联系时间到跟踪时间的间隔
     * 单位：分钟
     */
    Long followInterval

}
