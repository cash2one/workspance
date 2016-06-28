package com.nala.csd

/**
 * 自定义通知方式
 * 查件被动表
 * @author Kenny
 *
 */
class NotifyMode {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
}
