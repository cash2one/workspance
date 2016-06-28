/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustSell extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8081704120298950704L;
	private Integer id;
	private Integer companyId;
	private String status; // 审核通过表示报价被采纳，审核不通过表示报价被否决
	private String content;
	private Date gmtCreated;
	private Date gmtModified;

	private Integer buyId;
	private String buyNo;
	private String dealerName;
	private String dealerTel;
	private String dealerQQ;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getBuyNo() {
		return buyNo;
	}

	public void setBuyNo(String buyNo) {
		this.buyNo = buyNo;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerTel() {
		return dealerTel;
	}

	public void setDealerTel(String dealerTel) {
		this.dealerTel = dealerTel;
	}

	public String getDealerQQ() {
		return dealerQQ;
	}

	public void setDealerQQ(String dealerQQ) {
		this.dealerQQ = dealerQQ;
	}

	public Integer getBuyId() {
		return buyId;
	}

	public void setBuyId(Integer buyId) {
		this.buyId = buyId;
	}

}
