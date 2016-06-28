package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.spot.SpotAuctionLog;

/**
 *	author:kongsj
 *	date:2013-3-15
 */
public class SpotAuctionLogDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7697809628600060001L;

	private SpotAuctionLog spotAuctionLog;
	
	private String mobile; // 电话号码
	private String contact; // 联系人
	public SpotAuctionLog getSpotAuctionLog() {
		return spotAuctionLog;
	}
	public String getMobile() {
		return mobile;
	}
	public String getContact() {
		return contact;
	}
	public void setSpotAuctionLog(SpotAuctionLog spotAuctionLog) {
		this.spotAuctionLog = spotAuctionLog;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
}
