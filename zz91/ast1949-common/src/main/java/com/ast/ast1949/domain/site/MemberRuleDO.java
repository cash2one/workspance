/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23
 */
package com.ast.ast1949.domain.site;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author yuyonghui
 *
 */
public class MemberRuleDO extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Integer id;
	private String membershipCode;
	private String operation;
	private String operationCode;
	private String results;
	private Date gmtCreated;
	private Date gmtModified;
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	public String getMembershipCode() {
		return membershipCode;
	}
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
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
