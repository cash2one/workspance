package com.ast.ast1949.domain.phone;

import java.util.Date;

public class PhoneCallClickLog {
	private Integer id;
	private String callerTel;
	private Integer companyId;
	private Float clickFee;
	private Date gmtCreated;
	private Date gmtModified;
	private Date from;
	private Date to;

	public Date getFrom() {
		return from;
	}


	public void setFrom(Date from) {
		this.from = from;
	}


	public Date getTo() {
		return to;
	}


	public void setTo(Date to) {
		this.to = to;
	}


	public Integer getId() {
		return id;
	}
	

	public String getCallerTel() {
		return callerTel;
	}


	public void setCallerTel(String callerTel) {
		this.callerTel = callerTel;
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

	public Float getClickFee() {
		return clickFee;
	}

	public void setClickFee(Float clickFee) {
		this.clickFee = clickFee;
	}

}
