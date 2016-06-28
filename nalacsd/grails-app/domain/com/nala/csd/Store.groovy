package com.nala.csd

/**
 * 店铺表
 * @author Kenny
 *
 */
class Store {

    static constraints = {
        name blank: false, unique: true
    }
	
	/**
	 * 店铺名称
	 */
	String name

    /**
     * 是否tp店铺
     */
    boolean tp = false

}
