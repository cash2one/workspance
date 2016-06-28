package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PhoneBlacklist extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3008362752642458634L;
	private Integer id;
	private String phone;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer phoneLogId;
	private String checkPerson;//审核人
	private String blackReason;//被拉黑原因

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getBlackReason() {
		return blackReason;
	}

	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}

	public Integer getPhoneLogId() {
		return phoneLogId;
	}

	public void setPhoneLogId(Integer phoneLogId) {
		this.phoneLogId = phoneLogId;
	}

}
