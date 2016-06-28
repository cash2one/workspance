package com.nala.csd

/**
 * 处理结果
 * B2C顾客咨询登记表
 * @author Kenny
 *
 */
class B2CConsultResult {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
}
