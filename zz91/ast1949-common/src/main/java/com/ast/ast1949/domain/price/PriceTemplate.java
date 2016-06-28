package com.ast.ast1949.domain.price;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 *	author:kongsj
 *	date:2013-5-22
 */
public class PriceTemplate extends DomainSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3402353283819059549L;
	private Integer id;
	private Integer priceId;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
