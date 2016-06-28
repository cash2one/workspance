package com.ast.ast1949.domain.exhibit;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PreviouExhibitors extends DomainSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exhibitNum; //展位号
	private String companyName;//公司名称
	private Date gmtCreated;
	private Date gmtModified;
	private String website;
	private Integer exhibitTime;//展会时间
	private Integer sort;//展会排序
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Integer getExhibitTime() {
		return exhibitTime;
	}
	public void setExhibitTime(Integer exhibitTime) {
		this.exhibitTime = exhibitTime;
	}
	public String getExhibitNum() {
		return exhibitNum;
	}
	public void setExhibitNum(String exhibitNum) {
		this.exhibitNum = exhibitNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
