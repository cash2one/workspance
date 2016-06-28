/**
 * @author kongsj
 * @date 2015年5月11日
 * 
 */
package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustRelateDeal extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3589493317472102816L;
	private Integer id;
	private Integer buyId;
	private Integer dealerId;
	private Integer sellId;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBuyId() {
		return buyId;
	}

	public void setBuyId(Integer buyId) {
		this.buyId = buyId;
	}

	public Integer getDealerId() {
		return dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getSellId() {
		return sellId;
	}

	public void setSellId(Integer sellId) {
		this.sellId = sellId;
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
