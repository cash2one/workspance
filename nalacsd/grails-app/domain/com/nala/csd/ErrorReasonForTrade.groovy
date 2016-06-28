package com.nala.csd

/**
 * 异常原因
 * 所属异常单
 * @author Kenny
 *
 */
class ErrorReasonForTrade {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
	
	def nameForList(){
		if (name){
			int maxLength = 6
			if (name.length() > maxLength){
				return name.substring(0, maxLength) + "..."
			}else{
				return name
			}
		}
	}
}
