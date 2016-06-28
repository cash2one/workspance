package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsAutoRefresh extends DomainSupport {

	/**
	 * 供求信息自动刷新
	 * @author zhozuk
	 * date : 2013.1.16
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id ;
	private Integer companyId;
	private String refreshDate;
	private String refreshRate;   //刷新频率
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
	public String getRefreshRate() {
		return refreshRate;
	}
	public void setRefreshRate(String refreshRate) {
		this.refreshRate = refreshRate;
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
