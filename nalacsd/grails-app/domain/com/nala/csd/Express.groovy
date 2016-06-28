package com.nala.csd

/**
 * 快递公司
 * @author Kenny
 *
 */
class Express {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name
}
