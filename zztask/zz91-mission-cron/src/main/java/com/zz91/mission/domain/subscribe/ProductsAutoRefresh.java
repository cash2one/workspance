package com.zz91.mission.domain.subscribe;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于供求信息自动刷新
 * @author zhozuk
 *
 */
public class ProductsAutoRefresh implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id ;
	private Integer companyId;
	private String refreshDate;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getRefreshDate() {
		return refreshDate;
	}
	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
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
