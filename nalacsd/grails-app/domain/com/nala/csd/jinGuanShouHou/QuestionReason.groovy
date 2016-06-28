package com.nala.csd.jinGuanShouHou

/**
 * 问题类型 一项选择时 
 * 如果选择了漏发或者错发
 * 会出现这个问题原因
 * @author Kenny
 *
 */
class QuestionReason {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
}
