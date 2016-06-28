package com.ast.ast1949.domain.sample;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ContactClickLog extends DomainSupport {
	
	private static final long serialVersionUID = 7896919697744104403L;
	private Integer companyId; 
	private Integer targetId;
	private Date gmtCreated; 
	private Date gmtModified;
	private Float clickFee;
	private Integer clickScore;
	private Date from;
	private Date to;

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	private String name; // 公司名称

	public Integer getCompanyId() {
		return companyId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getClickFee() {
		return clickFee;
	}

	public void setClickFee(Float clickFee) {
		this.clickFee = clickFee;
	}

	public Integer getClickScore() {
		return clickScore;
	}

	public void setClickScore(Integer clickScore) {
		this.clickScore = clickScore;
	}
}
