package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class SubscribeSmsPriceDO extends DomainSupport{

	/**
	 * 生意管家短信报价
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private Integer areaNodeId;
	private String categoryCode;
	private Date gmtCreated;
	private Date gmtModified;
	private String name;
	private String areaName;
	
	public SubscribeSmsPriceDO() {
		super();
	}

	public SubscribeSmsPriceDO(Integer companyId, Integer areaNodeId,
			String categoryCode, Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.areaNodeId = areaNodeId;
		this.categoryCode = categoryCode;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Integer getAreaNodeId() {
		return areaNodeId;
	}

	public void setAreaNodeId(Integer areaNodeId) {
		this.areaNodeId = areaNodeId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


}
