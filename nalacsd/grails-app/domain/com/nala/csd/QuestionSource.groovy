package com.nala.csd

import java.util.Date;

/**
 * 咨询方式
 * B2C 顾客咨询登记表
 * @author Kenny
 *
 */
class QuestionSource {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
  
}
