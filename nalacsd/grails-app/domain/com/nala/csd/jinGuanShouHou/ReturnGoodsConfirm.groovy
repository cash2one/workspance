package com.nala.csd.jinGuanShouHou

/**
 * 牛盾系统售后表里面,货款操作记录 添加 选择收到退货待确认时弹出 子选项
 */
class ReturnGoodsConfirm {

    static constraints = {
        name blank: false, unique: true

    }
    
    String name
}
