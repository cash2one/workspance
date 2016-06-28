package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 竞拍小计 author:kongsj date:213-3-12
 */
public class SpotAuctionLog extends DomainSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8353261923333571906L;
	private Integer spotAuctionId;// 竞拍产品id
	private Integer companyId;// 联系人
	private Integer price;// 竞拍客户出价
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getSpotAuctionId() {
		return spotAuctionId;
	}

	public Integer getPrice() {
		return price;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setSpotAuctionId(Integer spotAuctionId) {
		this.spotAuctionId = spotAuctionId;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
