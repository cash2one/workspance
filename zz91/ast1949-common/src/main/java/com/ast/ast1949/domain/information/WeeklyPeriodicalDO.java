/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.domain.information;

import java.util.Date;

/**
 * @author yuyonghui
 *
 */
public class WeeklyPeriodicalDO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer periodicalNum;
	private Date publishTime;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPeriodicalNum() {
		return periodicalNum;
	}
	public void setPeriodicalNum(Integer periodicalNum) {
		this.periodicalNum = periodicalNum;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
