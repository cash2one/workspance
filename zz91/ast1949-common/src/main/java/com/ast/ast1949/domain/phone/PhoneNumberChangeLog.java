package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-7-3
 */
public class PhoneNumberChangeLog extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2980723666923615138L;
	private Integer id;
	private String telFrom;
	private String telTo;
	private String operator;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTelFrom() {
		return telFrom;
	}
	public void setTelFrom(String telFrom) {
		this.telFrom = telFrom;
	}
	public String getTelTo() {
		return telTo;
	}
	public void setTelTo(String telTo) {
		this.telTo = telTo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
