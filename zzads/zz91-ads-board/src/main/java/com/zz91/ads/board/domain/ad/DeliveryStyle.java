/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class DeliveryStyle implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;//投放方式名称
	private String jsFunction;//JS生成代码
	private Date gmtCreated;
	private Date gmtModified;
	private String modifier;//最后操作人：帐号名称

	public DeliveryStyle() {
		super();
	}
	
	public DeliveryStyle(String name, String jsFunction, Date gmtCreated,
			Date gmtModified, String modifier) {
		super();
		this.name = name;
		this.jsFunction = jsFunction;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.modifier = modifier;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
