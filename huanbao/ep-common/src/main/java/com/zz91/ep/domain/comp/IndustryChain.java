package com.zz91.ep.domain.comp;

import java.io.Serializable;
import java.util.Date;

public class IndustryChain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String categoryCode;
	private String categoryName;
	private String areaCode;
	private String areaName;
	private Integer showIndex;
	private Integer orderby;
	private Date gmtCreated;
	private Date gmtModified;
	private Short delStatus;
	
	
	public IndustryChain() {
		super();
		
	}
	public IndustryChain(Integer id, String categoryCode, String categoryName,
			String areaCode, String areaName, Integer showIndex,
			Integer orderby, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.showIndex = showIndex;
		this.orderby = orderby;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
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
	public Short getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Short delStatus) {
		this.delStatus = delStatus;
	}

}
