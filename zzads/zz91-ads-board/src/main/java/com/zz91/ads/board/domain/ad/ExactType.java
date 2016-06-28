/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class ExactType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String exactName;//精确投放类型：对应参数表 col:param_key
	private String jsFunction;//text,
	private String javaKey;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	
	public ExactType() {
		super();
	}
	
	public ExactType(Integer id, String exactName, String jsFunction, String javaKey,
			String remark, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.exactName = exactName;
		this.jsFunction = jsFunction;
		this.javaKey = javaKey;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

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
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
