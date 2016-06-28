package com.ast.ast1949.domain.sample;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class SampleMsgset extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7785105031054255283L;

	private Integer id;
	private Integer companyId;
	private Integer email;
	private Integer sms;
	private Integer wechat;
	private Date gmtCreated;
	private Date gmtModified;

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

	public Integer getEmail() {
		return email;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	public Integer getSms() {
		return sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
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

	public Integer getWechat() {
		return wechat;
	}

	public void setWechat(Integer wechat) {
		this.wechat = wechat;
	}

}