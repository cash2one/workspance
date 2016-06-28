/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.domain;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class CrmSaleStatistics implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date gmtTarget;
	private Integer zhejiang;
	private Integer jiangsu;
	private Integer shanghai;
	private Integer guangdong;
	private Integer shandong;
	private Integer beijing;
	private Integer hebei;
	private Integer other;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}
	public Integer getZhejiang() {
		return zhejiang;
	}
	public void setZhejiang(Integer zhejiang) {
		this.zhejiang = zhejiang;
	}
	public Integer getJiangsu() {
		return jiangsu;
	}
	public void setJiangsu(Integer jiangsu) {
		this.jiangsu = jiangsu;
	}
	public Integer getShanghai() {
		return shanghai;
	}
	public void setShanghai(Integer shanghai) {
		this.shanghai = shanghai;
	}
	public Integer getGuangdong() {
		return guangdong;
	}
	public void setGuangdong(Integer guangdong) {
		this.guangdong = guangdong;
	}
	public Integer getShandong() {
		return shandong;
	}
	public void setShandong(Integer shandong) {
		this.shandong = shandong;
	}
	public Integer getBeijing() {
		return beijing;
	}
	public void setBeijing(Integer beijing) {
		this.beijing = beijing;
	}
	public Integer getHebei() {
		return hebei;
	}
	public void setHebei(Integer hebei) {
		this.hebei = hebei;
	}
	public Integer getOther() {
		return other;
	}
	public void setOther(Integer other) {
		this.other = other;
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