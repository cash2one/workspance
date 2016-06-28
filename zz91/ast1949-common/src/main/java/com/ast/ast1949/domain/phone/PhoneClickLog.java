package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-7-16
 */
public class PhoneClickLog extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7896919697744104403L;
	private Integer companyId; // INT(11) NOT NULL COMMENT '点击公司id' ,
	private Integer targetId; // INT(11) NOT NULL COMMENT '被点击公司id' ,
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,
	private Float clickFee;
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

}
