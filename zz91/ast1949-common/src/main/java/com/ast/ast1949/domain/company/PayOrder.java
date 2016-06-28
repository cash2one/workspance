/**
 * @author kongsj
 * @date 2014年11月12日
 * 
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PayOrder extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7020583340568593829L;
	private Integer id;
	private String noOrder;
	private String dtOrder;
	private Integer companyId;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getDtOrder() {
		return dtOrder;
	}

	public void setDtOrder(String dtOrder) {
		this.dtOrder = dtOrder;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
