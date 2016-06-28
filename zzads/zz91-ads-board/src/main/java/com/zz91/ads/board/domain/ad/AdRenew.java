/**
 * @author shiqp
 * @date 2015-07-08
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

public class AdRenew {
	private Integer id; // 续费日志编号
	private Integer adId; // 广告日志编号
	private Date gmtStart; // 开始时间
	private Date gmtEnd; // 结束时间
	private String applicant;// 申请人
	private Date gmtApply; // 申请时间
	private String dealer; // 操作者
	private Date gmtDeal; // 操作时间
	private Date gmtCreated; // 创建时间
	private Date gmtModified; // 更新时间
	private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Date getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getGmtApply() {
		return gmtApply;
	}

	public void setGmtApply(Date gmtApply) {
		this.gmtApply = gmtApply;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public Date getGmtDeal() {
		return gmtDeal;
	}

	public void setGmtDeal(Date gmtDeal) {
		this.gmtDeal = gmtDeal;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
