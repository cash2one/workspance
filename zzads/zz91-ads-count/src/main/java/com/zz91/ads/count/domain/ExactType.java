/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-14
 */
package com.zz91.ads.count.domain;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-14
 */
public class ExactType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String exactName;
	private String jsFunction;
	private String javaKey;
	private String remark;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the exactName
	 */
	public String getExactName() {
		return exactName;
	}
	/**
	 * @param exactName the exactName to set
	 */
	public void setExactName(String exactName) {
		this.exactName = exactName;
	}
	/**
	 * @return the jsFunction
	 */
	public String getJsFunction() {
		return jsFunction;
	}
	/**
	 * @param jsFunction the jsFunction to set
	 */
	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}
	/**
	 * @return the javaKey
	 */
	public String getJavaKey() {
		return javaKey;
	}
	/**
	 * @param javaKey the javaKey to set
	 */
	public void setJavaKey(String javaKey) {
		this.javaKey = javaKey;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
