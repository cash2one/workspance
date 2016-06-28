package com.nala.csd

/**
 * 问题类型
 * 可维护
 * @author Kenny
 *
 */

class QuestionType {

    static constraints = {
        questionDescription(blank: false, unique: true)
    }
	
	// int value
	
	String questionDescription
	
}
